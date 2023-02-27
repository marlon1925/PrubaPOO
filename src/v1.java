import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class v1 {
    private JComboBox diaBox1;
    private JComboBox mesBox2;
    private JComboBox añoBox3;
    private JButton crearButton;
    private JPanel panel;
    Statement st;
    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    public v1(){

        try{
            con = getConnection();
            st = con.createStatement();
            rs = st.executeQuery("SELECT dia FROM dia");

            while (rs.next()){
                diaBox1.addItem(rs.getString("dia"));
            }
        }catch (HeadlessException | SQLException f){
            System.out.println(f);
        }
        try{
            con = getConnection();
            st = con.createStatement();
            rs = st.executeQuery("SELECT mes FROM mes");

            while (rs.next()){
                mesBox2.addItem(rs.getString("mes"));
            }
        }catch (HeadlessException | SQLException f){
            System.out.println(f);
        }
        try{
            con = getConnection();
            st = con.createStatement();
            rs = st.executeQuery("SELECT anio FROM anio");

            while (rs.next()){
                añoBox3.addItem(rs.getString("anio"));
            }
        }catch (HeadlessException | SQLException f){
            System.out.println(f);
        }

        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                con = getConnection();
                String dia = diaBox1.getSelectedItem().toString();
                String mes = mesBox2.getSelectedItem().toString();
                String anio = añoBox3.getSelectedItem().toString();


                try{

                    ps = con.prepareStatement("INSERT INTO FECHAS (Fecha)" + "values (?)");

                    ps.setString(1,dia + "/" + mes + "/" + anio);

                    int res = ps.executeUpdate();

                    if (res > 0){
                        JOptionPane.showMessageDialog(null, "La fecha se ha guardado con exito");
                    }else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
                    }
                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });
    }
    public static Connection getConnection() {
        Connection con = null;
        String base = "fecha";
        String url = "jdbc:mysql://localhost:3306/" + base;
        String user = "root";
        String password = "marlon";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        }catch (ClassCastException | SQLException e){
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("FECHA");
        frame.setContentPane(new v1().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500,300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


