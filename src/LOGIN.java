import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LOGIN {
    private JPasswordField password;
    private JTextField usuario1;
    private JButton iniciarButton;
    public JPanel inicio;
    private JComboBox comboBox1;

    public LOGIN() {
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ex) {
                String nombre= usuario1.getText();
                String Items = comboBox1.getSelectedItem().toString();
                String Password = password.getText();

                try {
                    MongoDatabase database = ConexionMongoDB.getDatabase(); // Obtener la base de datos
                    MongoCollection<Document> collection = database.getCollection("usuarios");

                    Document busqueda = new Document("usuario", nombre)
                                            .append("contraseña",Password)
                                              .append("ingreso", Items);
                    Document encontrado = collection.find(busqueda).first();


                    if (encontrado != null  && Items.equals("Administrador")) {
                        JFrame frame = new JFrame("ADMINISTRADOR");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setSize(600, 600);
                        frame.setPreferredSize(new Dimension(600, 600));
                        frame.setContentPane(new Administrador().administrador);
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);

                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Verifica que todos los campos esten correctos");




                    }
                    /*

                    // Crear el nuevo documento
                    Document nuevo = new Document("usuario", usuario1.getText());

                    // Insertar el documento en la colección
                    collection.insertOne(nuevo);

                    JOptionPane.showMessageDialog(null, "Usuario registrado correctamente.");
                    // ... (limpiar los campos del formulario, etc.)
                    */


                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al registrar el usuario.");
                }


            }

        });
        }
}
