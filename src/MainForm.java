import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JDialog {
    private JLabel jlTitre;
    private JPanel jpMain;
    private JLabel jlNom;
    private JTextField tfNom;
    private JLabel jlPrenom;
    private JTextField tfPrenom;
    private JTextField tfEmail;
    private JLabel jlPwd;
    private JPasswordField pfPwd;
    private JButton jbtAdd;
    private JButton jbtCancel;
    private JLabel jlEmail;
    private JPasswordField pfVerify;
    private JLabel jlVerify;
    private JButton btUpdate;

    public MainForm(JFrame parent) {
        super(parent);
        setTitle("Gestion des utilisateurs");
        setMinimumSize(new Dimension(500, 500));
        setContentPane(jpMain);
        setLocationRelativeTo(parent);
        setModal(false);
        setVisible(true);
        jbtAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                register();
            }
        });
        jbtCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });
        btUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clic sur update");
            }
        });
        btUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
    }

    private boolean elVerificatiõn(String nom, String prenom, String email, String password, String verify) {
        //vérification si les champs sont bien remplis
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs du formulaire",
                    "Essaie encore",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!password.equals(verify)) {
            JOptionPane.showMessageDialog(this,
                    "Les mots de passe ne correspondent pas",
                    "Essaie encore",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void register() {
        String nom = tfNom.getText();
        String prenom = tfPrenom.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(pfPwd.getPassword());
        String verify = String.valueOf(pfVerify.getPassword());

        //vérification si les champs sont bien remplis
        if (!elVerificatiõn(nom, prenom, email, password, verify)) {
            return;
        }

        User user = new User(nom, prenom, email, password);

        // Vérif user existe déjà !!
        User existingUser = Request.getUserByMail(user);
        if (existingUser != null) {
            JOptionPane.showMessageDialog(this,
                    "Le compte existe déjà !",
                    "Essaie encore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ajoutez l'utilisateur à la BDD
        User addedUser = Request.addUser(user);
        if (addedUser != null) {
            JOptionPane.showMessageDialog(this,
                    "Le compte " + addedUser.getEmail() + " a été ajouté en BDD.",
                    "Grand Succés ",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Une erreur s'est produite lors de l'ajout du compte.",
                    "⚡🔥❌📛🛑⛔ERREUR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public void update() {
        String nom = tfNom.getText();
        String prenom = tfPrenom.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(pfPwd.getPassword());
        String verify = String.valueOf(pfVerify.getPassword());

        // vérification si les champs sont bien remplis
        if(!elVerificatiõn(nom, prenom, email, password, verify)){
            return;
        }

        // Recherche l'utilisateur existant
        User existingUser = Request.getUserByMail(new User(nom, prenom, email, password));

        if (existingUser == null) {
            JOptionPane.showMessageDialog(this,
                    "Le compte n'existe pas !",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mise à jour de l'utilisateur
        User updatedUser = Request.updateUser(existingUser, nom, prenom, email, password);

        if(updatedUser != null){
            JOptionPane.showMessageDialog(this,
                    "Le compte " + updatedUser.getEmail() + " a été mis à jour en BDD.",
                    "Grand Succés",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de la mise à jour du compte :( ",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
