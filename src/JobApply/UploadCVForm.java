package JobApply;

import javax.swing.*;
import java.awt.*;

public class UploadCVForm extends JPanel {
    private final JTextField cvLinkField;

    public UploadCVForm() {
        setLayout(new BorderLayout(8, 8));
        add(new JLabel("Please upload the link to your CV:"), BorderLayout.NORTH);

        cvLinkField = new JTextField(30);
        add(cvLinkField, BorderLayout.CENTER);

        add(new JLabel("(Ανέβασε CV ρε ύπνε θες και δουλειά)"), BorderLayout.SOUTH);
    }

    public String getCvLink() {
        return cvLinkField.getText();
    }
}