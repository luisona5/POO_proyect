import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import static java.util.Collection.*;


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
                MongoDatabase database = ConexionMongoDB.getDatabase(); // Obtener la base de datos
                MongoCollection<Document> collection = database.getCollection("usuarios");


                int filaSeleccionada = table1.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Selecciona un socio para modificar");
                    return;
                }
                String id = table1.getValueAt(filaSeleccionada, 0).toString();
                String nuevocedula = textField2.getText();
                String nuevonombre = textField1.getText();
                String nuevotelefono = textField3.getText();
                String nuevocorreo = textField6.getText();
                String nuevodireccion = textField4.getText();
                String nuevoingreso = comboBox1.getSelectedItem().toString();

                Document busqueda = new Document("_id", new ObjectId(id));
                Document actualizado = new Document("$set", new Document(("usuario" , nuevonombre)
                        .append("cedula", nuevocedula)
                        .append("ingreso", nuevoingreso)
                        .append("correo", nuevocorreo)
                        .append("direccion", nuevodireccion)
                        .append("telefono", nuevotelefono));
            }
        });

    ;}
}



