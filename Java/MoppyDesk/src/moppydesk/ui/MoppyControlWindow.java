/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moppydesk.ui;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import moppydesk.*;

/**
 *
 * @author Sam
 */
public class MoppyControlWindow extends javax.swing.JFrame {

    private static int MIDI_CHANNELS = 16;
    MoppyUI app;
    HashMap<String, Info> availableMIDIOuts;
    OutputSetting[] outputSettings = new OutputSetting[MIDI_CHANNELS];
    
    HashMap<String,Receiver> outputPlayers = new HashMap<String, Receiver>();

    /**
     * Creates new form MoppyControlWindow
     */
    public MoppyControlWindow(MoppyUI app) {
        this.app = app;
        availableMIDIOuts = MoppyMIDIBridge.getMIDIOutInfos();
        loadOutputSettings();

        initComponents();

        setupOutputControls();
    }

    private void loadOutputSettings() {
        OutputSetting[] os = (OutputSetting[]) app.getPreferenceObject(Constants.PREF_OUTPUT_SETTINGS);
        if (os == null) {
            for (int i = 1; i <= 16; i++) {
                outputSettings[i - 1] = new OutputSetting(i);
            }
            app.putPreferenceObject(Constants.PREF_OUTPUT_SETTINGS, outputSettings);
        } else {
            outputSettings = os;
        }
    }

    private void setupOutputControls() {


        for (OutputSetting s : outputSettings) {
            ChannelOutControl newControl = new ChannelOutControl(this, s);
            //TODO Read in preferences here?  Serialize all properties to preferences?
            mainOutputPanel.add(newControl);
        }
        mainOutputPanel.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        mainStatusLabel = new javax.swing.JLabel();
        mainInputPanel = new javax.swing.JPanel();
        inputSelectBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        mainOutputPanel = new javax.swing.JPanel();
        connectButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Moppy Control Application");

        mainStatusLabel.setText("Loaded.");
        mainStatusLabel.setToolTipText("Current status");

        mainInputPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mainInputPanel.setPreferredSize(new java.awt.Dimension(350, 400));

        inputSelectBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MIDI File", "MIDI IN Port" }));

        jLabel1.setText("Input Mode");

        mainOutputPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mainOutputPanel.setPreferredSize(new java.awt.Dimension(350, 400));
        mainOutputPanel.setLayout(new javax.swing.BoxLayout(mainOutputPanel, javax.swing.BoxLayout.Y_AXIS));

        connectButton.setText("Connect");
        connectButton.setToolTipText("Saves current output settings and connects as specified.");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputSelectBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(493, 493, 493)
                                .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(mainInputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mainOutputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(mainStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputSelectBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mainInputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainOutputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(connectButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainStatusLabel)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connect() {
        try {
            
            //Disable and save output settings...
            //TODO Disable output controls
            app.putPreferenceObject(Constants.PREF_OUTPUT_SETTINGS, outputSettings);
            app.savePreferences();
            
            setStatus("Initializing Receivers...");
            initializeReceivers();
            
            enableInputDevice();
            
            setStatus("Connected.");
        } catch (Exception ex) {
            Logger.getLogger(MoppyControlWindow.class.getName()).log(Level.SEVERE, null, ex);
            //TODO Show message
            disconnect();
        }
    }

    private void disconnect() {
        disableInputDevice();
        setStatus("Disconnecting...");
        app.rm.close();
        //Reenable output settings
        //TODO Enable output controls
        
        setStatus("Disconnected.");
    }

    private void initializeReceivers() throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException, MidiUnavailableException {
        app.rm.close();
        Arrays.fill(app.outputReceivers, null);
        
        outputPlayers.clear();
        
        for (int ch = 0; ch < 16; ch++) {
            OutputSetting os = outputSettings[ch];
            if (os.enabled) {
                // MoppyPlayer/Receivers are grouped by COM port
                if (os.type.equals(OutputSetting.OutputType.MOPPY)) {
                    if (!outputPlayers.containsKey(os.comPort)){
                        outputPlayers.put(os.comPort, new MoppyPlayer(new MoppyBridge(os.comPort)));
                    }
                    app.outputReceivers[ch] = outputPlayers.get(os.comPort);
                } 
                //MIDIPlayer/Receivers are grouped by MIDI output name
                else if (os.type.equals(OutputSetting.OutputType.MIDI)) {
                    if (!outputPlayers.containsKey(os.midiDeviceName)){
                        outputPlayers.put(os.midiDeviceName, new MoppyMIDIBridge(os.midiDeviceName));
                    }
                    app.outputReceivers[ch] = outputPlayers.get(os.midiDeviceName);
                }
            }
        }
    }

    private void enableInputDevice(){
        inputSelectBox.setEnabled(false);
        if (inputSelectBox.getSelectedIndex() == 0){ //MIDI File
            MoppySequencer seq = app.initializeSequencer();
            SequencerControls secC = new SequencerControls(app, this, seq);
            seq.addListener(secC);
            mainInputPanel.add(secC);
        } else { //MIDI IN
            
        }
    }
    
    private void disableInputDevice(){
        if (app.ms!=null){
            app.ms.closeSequencer();
        }
        mainInputPanel.removeAll();
        mainInputPanel.repaint();
        inputSelectBox.setEnabled(true);
    }
    
    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        connectButton.setEnabled(false);
        if (connectButton.getText().equals("Connect")) {
            connect();
            connectButton.setText("Disconnect");
        } else {
            disconnect();
            connectButton.setText("Connect");
        }
        connectButton.setEnabled(true);
    }//GEN-LAST:event_connectButtonActionPerformed

    public void setStatus(String newStatus) {
        mainStatusLabel.setText(newStatus);
        mainStatusLabel.repaint();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton connectButton;
    private javax.swing.JComboBox inputSelectBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel mainInputPanel;
    private javax.swing.JPanel mainOutputPanel;
    private javax.swing.JLabel mainStatusLabel;
    // End of variables declaration//GEN-END:variables
}
