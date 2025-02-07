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
    private JTextField cedula;

    public LOGIN() {
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ex) {
                String nombre= usuario1.getText();
                String Items = comboBox1.getSelectedItem().toString();
                String DNI= cedula.getText();

                try {

                    MongoDatabase database = ConexionMongoDB.getDatabase(); // Obtener la base de datos
                    MongoCollection<Document> collection = database.getCollection("usuarios");

                    // ingresando usuario por defecto

                        /*
                    Document administrador= new Document("usuario", "luis")
                                                 .append("cedula","123")
                                                 .append("ingreso", "Administrador");
                    collection.insertOne(administrador);

                    */
                    //  BUSCAR A LA PERSONA PARA PODER INGRESAR

                    Document busqueda = new Document("usuario", nombre)
                                            .append("cedula",DNI)
                                              .append("ingreso", Items);
                    Document encontrado = collection.find(busqueda).first();




                    if (encontrado != null  && Items.equals("Administrador")) {
                        JFrame frame = new JFrame("ADMINISTRADOR");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setSize(600, 600);
                        frame.setPreferredSize(new Dimension(600, 400));
                        frame.setContentPane(new Administrador().administrador);
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);

                    }
                    else if (encontrado != null && Items.equals("Usuario")) {

                        JFrame frame = new JFrame("consultas de reservas");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setSize(600, 600);
                        frame.setPreferredSize(new Dimension(600, 400));
                        frame.setContentPane(new Usuario().Vusuarios);
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);


                    } else{

                        JOptionPane.showMessageDialog(null, "Verifica que todos los campos esten correctos");

                    }



                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }


            }

        });
        }
}
