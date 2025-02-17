import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import static java.util.Collection.*;
import static org.apache.commons.logging.LogFactory.objectId;


public class Administrador {
    private JTextField textField1;
    public JPanel administrador;
    private JTextField textField2;
    private JTextField textField3;
    private JComboBox comboBox1;
    private JButton siguienteButton;
    private JTextField textField4;
    private JTextField textField6;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton guardarButton;
    private JTable table1;
    private DefaultTableModel tabla;


    public Administrador() {



        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = textField2.getText();
                String nombre = textField1.getText();
                String telefono = textField3.getText();
                String correo = textField6.getText();
                String direccion = textField4.getText();
                String ingreso = comboBox1.getSelectedItem().toString();

                try {
                    MongoDatabase database = ConexionMongoDB.getDatabase(); // Obtener la base de datos
                    MongoCollection<Document> collection = database.getCollection("usuarios");

                    Document nuevo = new Document("usuario", nombre)
                            .append("cedula", cedula)
                            .append("ingreso", ingreso)
                            .append("correo", correo)
                            .append("direccion", direccion)
                            .append("telefono", telefono);
                    collection.insertOne(nuevo);

                    tabla = new DefaultTableModel(new String[]{"ID","cedula", "usuario", "telefono", "correo", "direccion","ingreso"}, 0);
                    table1.setModel(tabla);
                    tabla.setRowCount(0);
                    FindIterable<Document> vistazo = collection.find();
                    for (Document doc : vistazo) {
                        // Crea un array de objetos con los datos del documento
                        Object[] rowData = {
                                doc.getObjectId("_id"),
                                doc.getString("cedula"),
                                doc.getString("usuario"),
                                doc.getString("telefono"),
                                doc.getString("correo"),
                                doc.getString("direccion"),
                                doc.getString("ingreso")
                        };
                        tabla.addRow(rowData);
                    }


                    // validando los campos
                    if (ingreso.equals("-Seleccionar-") || cedula.isEmpty() || nombre.isEmpty()
                        || telefono.isEmpty() || correo.isEmpty() || direccion.isEmpty()){


                        JOptionPane.showMessageDialog(null, "Todos los campos deben seer llenados.");
                        return;
                    }else{
                        JOptionPane.showMessageDialog(null, "usuario registrado con exito");

                    }

                } catch (Exception ex) {
                    throw new RuntimeException(ex);

                }
            }
        });


        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MongoDatabase database = ConexionMongoDB.getDatabase();
                    MongoCollection<Document> collection = database.getCollection("usuarios");

                    int filaSeleccionada = table1.getSelectedRow();
                    if (filaSeleccionada == -1) {
                        JOptionPane.showMessageDialog(null, "Seleccione un usuario para editar la información");
                        return;
                    }


                    String id = table1.getValueAt(filaSeleccionada, 0).toString();
                    String nuevocedula = textField2.getText();
                    String nuevonombre = textField1.getText();
                    String nuevotelefono = textField3.getText();
                    String nuevocorreo = textField6.getText();
                    String nuevodireccion = textField4.getText();
                    String nuevoingreso = comboBox1.getSelectedItem().toString();



                    Document busqueda = new Document();

                    Document actualizado = new Document("$set", new Document("cedula", nuevocedula)
                            .append("nombre", nuevonombre)
                            .append("telefono", nuevotelefono)
                            .append("correo", nuevocorreo)
                            .append("direccion", nuevodireccion)
                            .append("ingreso", nuevoingreso));

                    collection.updateOne(busqueda, actualizado);

                    JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente");

                    // Consulta todos los usuarios
                    FindIterable<Document> usuarios = collection.find();

                    DefaultTableModel modelo = (DefaultTableModel) table1.getModel();

                    // Itera sobre los resultados y agrega cada usuario a la tabla
                    for (Document usuario : usuarios) {
                        Object[] fila = {
                                usuario.get("_id").toString(),
                                usuario.get("cedula"),
                                usuario.get("nombre"),
                                usuario.get("telefono"),
                                usuario.get("correo"),
                                usuario.get("direccion"),
                                usuario.get("ingreso")
                        };
                        modelo.addRow(fila);
                    }


                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar usuario: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }


        });

    ;
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MongoDatabase database = ConexionMongoDB.getDatabase(); // Obtener la base de datos
                    MongoCollection<Document> collection = database.getCollection("usuarios");


                    int filaSeleccionada = table1.getSelectedRow();
                    if (filaSeleccionada == -1) {
                        JOptionPane.showMessageDialog(null, " no ha seleccionado persona para la eliminacion\nporfsvot elija uno");
                        return;
                    }
                    String id = table1.getValueAt(filaSeleccionada, 0).toString();
                    collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
                    // Actualizando la tabla
                    DefaultTableModel tabla = (DefaultTableModel) table1.getModel();
                    tabla.setRowCount(0); // Limpiar la tabla

                    FindIterable<Document> vistazo = collection.find();
                    for (Document doc : vistazo) {
                        tabla.addRow(new Object[]{
                                doc.getObjectId("_id").toString(),
                                doc.getString("cedula"),
                                doc.getString("usuario"),
                                doc.getString("telefono"),
                                doc.getString("correo"),
                                doc.getString("direccion"),
                                doc.getString("tipo")
                        });
                    }

                    JOptionPane.showMessageDialog(null, "Usuario eliminado con exito");

                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + ex.getMessage());
                }

            }
        });
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("cantidad");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 600);
                frame.setPreferredSize(new Dimension(600, 600));
                frame.setContentPane(new Contenido().contenido);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}



