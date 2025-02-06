import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Administrador {
    private JTextField textField1;
    public JPanel administrador;
    private JTextField textField2;
    private JTextField textField3;
    private JComboBox comboBox1;
    private JButton siguienteButton;

    public Administrador() {
        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    MongoDatabase database = ConexionMongoDB.getDatabase(); // Obtener la base de datos
                    MongoCollection<Document> collection = database.getCollection("usuarios");

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
    }
}
