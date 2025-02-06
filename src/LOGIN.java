import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LOGIN {
    private JPasswordField password;
    private JTextField usuario1;
    private JButton iniciarButton;
    public JPanel inicio;

    public LOGIN() {
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ex) {

                try {
                    MongoDatabase database = ConexionMongoDB.getDatabase(); // Obtener la base de datos
                    MongoCollection<Document> collection = database.getCollection("usuarios");

                    // Crear el nuevo documento
                    Document nuevo = new Document("usuario", usuario1.getText());

                    // Insertar el documento en la colección
                    collection.insertOne(nuevo);

                    JOptionPane.showMessageDialog(null, "Usuario registrado correctamente.");
                    // ... (limpiar los campos del formulario, etc.)

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al registrar el usuario.");
                }


            }

        });
        }
}
