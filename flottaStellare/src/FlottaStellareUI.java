
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList;

public class FlottaStellareUI extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FlottaStellareUI.class.getName());

    /**
     * Creates new form FlottaStellareUI
     */
    Viaggio viaggio;
    DefaultListModel<String> listaNavi = new DefaultListModel<>();
    DefaultListModel<String> listaMembri = new DefaultListModel<>();
    DefaultListModel<String> listaModuli = new DefaultListModel<>();
    
    public FlottaStellareUI(){
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        lbl_salute.setText("");
        lbl_ruolo.setText("");
    }
    
    public String creaFinestraDialogo(String contesto){
        final String[] input = {null};

        JDialog dialog = new JDialog(this, "Input", true);

        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel questionLabel = new JLabel(contesto);

        questionLabel.setFont(questionLabel.getFont().deriveFont(14f));

        mainPanel.add(questionLabel, BorderLayout.NORTH);

        JTextField textField = new JTextField(20);

        mainPanel.add(textField, BorderLayout.CENTER);

        ActionListener submitAction = e -> {
            if (!textField.getText().trim().isEmpty()) {
                input[0] = textField.getText();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Input necessario!");
            }
        };
        
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(60, 30)); 
        okButton.addActionListener(submitAction);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        textField.addActionListener(submitAction);

        dialog.add(mainPanel);

        dialog.pack();

        dialog.setLocationRelativeTo(this);

        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                textField.requestFocusInWindow();
            }
        });

        dialog.setVisible(true);

        return input[0];
    }
    
    public <T> T creaFinestraScelta(String contesto, ArrayList<T> opzioni){
    final Object[] input = {null};
    JDialog dialog = new JDialog(this, "Input", true);
    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    
    JLabel questionLabel = new JLabel(contesto);
    questionLabel.setFont(questionLabel.getFont().deriveFont(14f));
    mainPanel.add(questionLabel, BorderLayout.NORTH);
    
    // Create combo box with ArrayList values
    JComboBox<T> comboBox = new JComboBox<>(opzioni.toArray((T[]) new Object[0]));
    mainPanel.add(comboBox, BorderLayout.CENTER);
    
    ActionListener submitAction = e -> {
        input[0] = comboBox.getSelectedItem();
        dialog.dispose();
    };
    
    // Create OK button
    JButton okButton = new JButton("OK");
    okButton.setPreferredSize(new Dimension(60, 30));
    okButton.addActionListener(submitAction);
    
    // Create button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(okButton);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    
    dialog.add(mainPanel);
    dialog.pack();
    dialog.setLocationRelativeTo(this);
    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowOpened(java.awt.event.WindowEvent e) {
            comboBox.requestFocusInWindow();
        }
    });
    dialog.setVisible(true);
    return (T) input[0];
}
    
    public <T> ArrayList<T> creaFinestraSceltaMultipla(String contesto, ArrayList<T> opzioni){
    final ArrayList<T> selectedItems = new ArrayList<>();
    JDialog dialog = new JDialog(this, "Selezione Multipla", true);
    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    
    JLabel questionLabel = new JLabel(contesto);
    questionLabel.setFont(questionLabel.getFont().deriveFont(14f));
    mainPanel.add(questionLabel, BorderLayout.NORTH);
    
    // Create panel for checkboxes
    JPanel checkBoxPanel = new JPanel();
    checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
    
    // Create a checkbox for each option
    ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
    for (T opzione : opzioni) {
        JCheckBox checkBox = new JCheckBox(opzione.toString());
        checkBox.setFocusPainted(false); // Remove focus outline
        checkBoxes.add(checkBox);
        checkBoxPanel.add(checkBox);
    }
    
    // Calculate height based on number of checkboxes (max 10)
    int numCheckboxes = Math.min(opzioni.size(), 10);
    int checkboxHeight = 25; // Approximate height per checkbox
    int scrollPaneHeight = numCheckboxes * checkboxHeight;
    
    // Add scroll pane only if needed
    JScrollPane scrollPane = new JScrollPane(checkBoxPanel);
    scrollPane.setPreferredSize(new Dimension(300, scrollPaneHeight));
    scrollPane.setBorder(null); // Remove scroll pane border
    mainPanel.add(scrollPane, BorderLayout.CENTER);
    
    ActionListener submitAction = e -> {
        selectedItems.clear();
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isSelected()) {
                selectedItems.add(opzioni.get(i));
            }
        }
        dialog.dispose();
    };
    
    // Create OK button
    JButton okButton = new JButton("OK");
    okButton.setPreferredSize(new Dimension(60, 30));
    okButton.addActionListener(submitAction);
    
    // Create button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(okButton);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    
    dialog.add(mainPanel);
    dialog.pack();
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
    
    return selectedItems;
}
    
    public String stringInput(String contesto){
        String input="";
        while(input.trim().length()==0){
            input = creaFinestraDialogo(contesto);
        }
        return input;
    }
    public int intInput(String contesto){
        int input=0;
        while(input<=0){
            try{
                input = Integer.parseInt(creaFinestraDialogo(contesto));
            }catch(Exception e){}
        }
        return input;
    }
    public Ruoli ruoloInput(String contesto){
        return creaFinestraScelta(contesto, Ruoli.getRuoli());
    }
    public ArrayList<TipiModulo> tipiModuloInput(String contesto){
        return creaFinestraSceltaMultipla(contesto, TipiModulo.getTipiModulo());
    }
    
    public void aggiornaNavi(){
        listaNavi.clear();
        for(Astronave a : viaggio.getNavi()){
            listaNavi.addElement(a.getNome());
        }
    }
    public void aggiornaMembri(){
        listaMembri.clear();
        for(Membro m : viaggio.getMembri(lst_navi.getSelectedIndex())){
            listaMembri.addElement(m.getNome());
        }
    }
    public void aggiornaModuli(){
        listaModuli.clear();
        for(Modulo m : viaggio.getModuli(lst_navi.getSelectedIndex())){
            listaModuli.addElement(m.getTipo().name());
        }
    }
    
    public void inizializzazione(){
        Flotta f = new Flotta(stringInput("Dimmi il nome della flotta"), intInput("Dimmi il numero di razioni per membro"));
        viaggio = new Viaggio(intInput("Dimmi il numero di giorni"), f);
        jLabel1.setText("Nome della flotta: "+viaggio.getFlotta().getNome());
        lbl_giorniRimasti.setText("giorni rimasti: "+ viaggio.getGiorniRimasti());
        lbl_razioniRimaste.setText("razioni rimaste: "+ viaggio.getFlotta().getRazioni());
        
        lst_navi.setModel(listaNavi);
        lst_moduli.setModel(listaModuli);
        lst_membri.setModel(listaMembri);
    }
    
    public void mostraMessaggio(String m){
        JOptionPane.showMessageDialog(this, m, "Dialog Title", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lst_navi = new javax.swing.JList<>();
        btn_aggiungiNave = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lst_moduli = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        lst_membri = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbl_ruolo = new javax.swing.JLabel();
        lbl_salute = new javax.swing.JLabel();
        lbl_razioniRimaste = new javax.swing.JLabel();
        lbl_giorniRimasti = new javax.swing.JLabel();
        btn_aggiungiMembro = new javax.swing.JButton();
        btn_aggiungiModulo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        lst_navi.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lst_navi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lst_naviMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lst_navi);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 110, 440, 430);

        btn_aggiungiNave.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btn_aggiungiNave.setText("Aggiungi una nave");
        btn_aggiungiNave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_aggiungiNaveMouseClicked(evt);
            }
        });
        getContentPane().add(btn_aggiungiNave);
        btn_aggiungiNave.setBounds(70, 550, 220, 40);

        lst_moduli.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lst_moduli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lst_moduliMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(lst_moduli);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(490, 350, 230, 190);

        lst_membri.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lst_membri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lst_membriMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(lst_membri);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(490, 110, 230, 200);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Nome della flotta:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(240, 10, 430, 40);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("moduli");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(570, 320, 70, 20);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("navi");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(200, 80, 37, 20);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("membri");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(570, 80, 70, 20);

        lbl_ruolo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_ruolo.setText("ruolo:");
        getContentPane().add(lbl_ruolo);
        lbl_ruolo.setBounds(730, 200, 160, 20);

        lbl_salute.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_salute.setText("salute:");
        getContentPane().add(lbl_salute);
        lbl_salute.setBounds(730, 430, 160, 20);

        lbl_razioniRimaste.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_razioniRimaste.setText("razioni rimaste:");
        getContentPane().add(lbl_razioniRimaste);
        lbl_razioniRimaste.setBounds(800, 50, 230, 25);

        lbl_giorniRimasti.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_giorniRimasti.setText("giorni rimasti: ");
        getContentPane().add(lbl_giorniRimasti);
        lbl_giorniRimasti.setBounds(800, 20, 230, 25);

        btn_aggiungiMembro.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btn_aggiungiMembro.setText("Aggiungi membri");
        btn_aggiungiMembro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_aggiungiMembroMouseClicked(evt);
            }
        });
        getContentPane().add(btn_aggiungiMembro);
        btn_aggiungiMembro.setBounds(70, 600, 220, 40);

        btn_aggiungiModulo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btn_aggiungiModulo.setText("Aggiungi moduli");
        btn_aggiungiModulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_aggiungiModuloMouseClicked(evt);
            }
        });
        getContentPane().add(btn_aggiungiModulo);
        btn_aggiungiModulo.setBounds(70, 650, 220, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_aggiungiNaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_aggiungiNaveMouseClicked
        if(!viaggio.aggiungiNave(stringInput("Dimmi il nome della nave"))){
            mostraMessaggio("Impossibile aggiungere una nave con tale nome");
            return;
        }
        mostraMessaggio("Nave aggiunta");
        aggiornaNavi();
        lbl_razioniRimaste.setText("razioni rimaste: "+ viaggio.getFlotta().getRazioni());
    }//GEN-LAST:event_btn_aggiungiNaveMouseClicked

    private void btn_aggiungiMembroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_aggiungiMembroMouseClicked
        if(lst_navi.getSelectedIndex() == -1){ 
            mostraMessaggio("Impossibile aggiungere il membro");
            return;
        }
        viaggio.aggiungiMembri(viaggio.getNavi().get(lst_navi.getSelectedIndex()),intInput("Dimmi il numero dei membri da aggiungere"), ruoloInput("Dimmi il ruolo dei membri da aggiungere"));
        aggiornaMembri();
        lbl_razioniRimaste.setText("razioni: "+viaggio.getFlotta().getRazioni());
        mostraMessaggio("Membri aggiunti");
    }//GEN-LAST:event_btn_aggiungiMembroMouseClicked

    private void btn_aggiungiModuloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_aggiungiModuloMouseClicked
        if(lst_navi.getSelectedIndex() == -1){
            mostraMessaggio("Impossibile aggiungere il modulo");
            return;
        }
        //da modificare
        viaggio.aggiungiModulo(viaggio.getNavi().get(lst_navi.getSelectedIndex()), intInput("Dimmi la salute dei moduli"), tipiModuloInput("Dimmi il tipo dei moduli"));
        aggiornaModuli();
        mostraMessaggio("Moduli aggiunti");
    }//GEN-LAST:event_btn_aggiungiModuloMouseClicked

    private void lst_naviMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lst_naviMouseClicked
        if(lst_navi.getSelectedIndex()!=-1){
            aggiornaModuli();
            aggiornaMembri();
            lst_membri.clearSelection();
            lst_moduli.clearSelection();
            lbl_ruolo.setText("");
            lbl_salute.setText("");
        }
    }//GEN-LAST:event_lst_naviMouseClicked

    private void lst_moduliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lst_moduliMouseClicked
        if(lst_moduli.getSelectedIndex()!=-1){
            lst_membri.clearSelection();
            lbl_ruolo.setText("");
            lbl_salute.setText("salute: " + viaggio.getModuli(lst_navi.getSelectedIndex()).get(lst_moduli.getSelectedIndex()).getSalute());
        }
    }//GEN-LAST:event_lst_moduliMouseClicked

    private void lst_membriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lst_membriMouseClicked
        if(lst_membri.getSelectedIndex()!=-1){
            lst_moduli.clearSelection();
            lbl_salute.setText("");
            lbl_ruolo.setText("ruolo: " + viaggio.getFlotta().getAstronaviIntatte().get(lst_navi.getSelectedIndex()).getMembriVivi().get(lst_membri.getSelectedIndex()).getRuolo().name());lst_moduli.clearSelection();
        }
    }//GEN-LAST:event_lst_membriMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FlottaStellareUI().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_aggiungiMembro;
    private javax.swing.JButton btn_aggiungiModulo;
    private javax.swing.JButton btn_aggiungiNave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbl_giorniRimasti;
    private javax.swing.JLabel lbl_razioniRimaste;
    private javax.swing.JLabel lbl_ruolo;
    private javax.swing.JLabel lbl_salute;
    private javax.swing.JList<String> lst_membri;
    private javax.swing.JList<String> lst_moduli;
    private javax.swing.JList<String> lst_navi;
    // End of variables declaration//GEN-END:variables
}
