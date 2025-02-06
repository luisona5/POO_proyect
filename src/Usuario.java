import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Usuario {
    public JPanel Vusuarios;
    private JTextField cedula;
    private JTextField nombre;
    private JTextField apellido;
    private JButton consultarButton;

    public Usuario() {
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MongoDatabase database = ConexionMongoDB.getDatabase(); // Obtener la base de datos
                    MongoCollection<Document> collection = database.getCollection("Administrador");


                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }
}
