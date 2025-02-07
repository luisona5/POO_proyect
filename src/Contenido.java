import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Contenido {
    public JPanel contenido;
    private JButton guardarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JComboBox comboBox1;
    private JTextField descripcion;
    private JTable table2;
    private DefaultTableModel tablacontenido;


    public Contenido() {
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = comboBox1.getSelectedItem().toString();
                String descripcion1 = descripcion.getText();

                try {
                    MongoDatabase database = ConexionMongoDB.getDatabase(); // Obtener la base de datos
                    MongoCollection<Document> collection = database.getCollection("usuarios");

                    Document contenido = new Document("seleccion", seleccion)
                            .append("despripcion", descripcion1);
                    collection.insertOne(contenido);

                    tablacontenido = new DefaultTableModel(new String[]{"ID","seleccion","descripcion"}, 0);
                    table2.setModel(tablacontenido);
                    tablacontenido.setRowCount(0);
                    FindIterable<Document> viztaso = collection.find();
                    for (Document doc : viztaso) {
                        tablacontenido.addRow(new Object[] {
                                doc.getString("_id"),
                                doc.getString("seleccion"),
                                doc.getString("descripcion"),
                        });
                    }

                    // validando los campos
                    if (descripcion1 == null || seleccion == "-Sleccionar-") {

                        JOptionPane.showMessageDialog(null, "Todos los campos deben ser llenados.");
                        return;
                    }

                } catch (Exception ex) {
                    throw new RuntimeException(ex);

                }

            }
        });
    }
}
