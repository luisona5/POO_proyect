import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



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

    public Administrador() {

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String DNI = textField2.getText();
                String nombre = textField1.getText();
                String telefono = textField3.getText();
                String correo = textField6.getText();
                String direccion = textField4.getText();
                String Usuarioregistro = comboBox1.getSelectedItem().toString();

                try {
                    MongoDatabase database = ConexionMongoDB.getDatabase(); // Obtener la base de datos
                    MongoCollection<Document> collection = database.getCollection("usuarios");

                    Document nuevo = new Document("usuario", nombre)
                            .append("cedula", DNI)
                            .append("ingreso", Usuarioregistro)
                            .append("correo", correo)
                            .append("direccion", direccion)
                            .append("telefono", telefono);
                    collection.insertOne(nuevo);

                    if(nuevo != null && Usuarioregistro.equals("Usuario")) {
                        JOptionPane.showMessageDialog(null, "Usuario guardado correctamente");
                    }


                } catch (Exception ex) {
                    throw new RuntimeException(ex);

                }


            }
        });
    }
}

