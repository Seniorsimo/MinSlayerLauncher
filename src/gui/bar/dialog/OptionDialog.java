/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bar.dialog;

import gui.LauncherPanel;
import gui.MainDialog;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import moduli.Controller;
import moduli.OptionManager;

/**
 *
 * @author simone
 */
public class OptionDialog extends MainDialog{
    
    private Controller controller;
    private JTextField x,y;
    private JCheckBox full;
    private JComboBox<String> memorySelect;
    private JComboBox<String> optiSelect;
    
    public OptionDialog(Controller controller){
        super();
        this.controller = controller;
        
        //edit
        this.setTitle("Setting");
        LauncherPanel main = new LauncherPanel();
        main.setLayout(new BorderLayout());
        
        LauncherPanel video = new LauncherPanel();
        video.setLayout(new BorderLayout());
        video.add(new JLabel("Video"), BorderLayout.NORTH);
        LauncherPanel videoPanel = new LauncherPanel();
        videoPanel.setLayout(new GridLayout(2,1));
        LauncherPanel videoPanelRow1 = new LauncherPanel();
        videoPanelRow1.setLayout(new FlowLayout());
        videoPanelRow1.add(new JLabel("x: "));
        x = new JTextField(5);
        videoPanelRow1.add(x);
        videoPanelRow1.add(new JLabel("   y: "));
        y = new JTextField(5);
        videoPanelRow1.add(y);
        videoPanel.add(videoPanelRow1);
        LauncherPanel videoPanelRow2 = new LauncherPanel();
        videoPanelRow2.setLayout(new FlowLayout());
        videoPanelRow2.add(new JLabel("Fullscreen "));
        full = new JCheckBox();
        videoPanelRow2.add(full);
        videoPanel.add(videoPanelRow2);
        video.add(videoPanel, BorderLayout.CENTER);
        main.add(video, BorderLayout.NORTH);
        
        LauncherPanel memory = new LauncherPanel();
        memory.setLayout(new BorderLayout());
        memory.add(new JLabel("Java memory"), BorderLayout.NORTH);
        LauncherPanel memoryPanel = new LauncherPanel();
        memoryPanel.setLayout(new FlowLayout());
        memoryPanel.add(new JLabel("Max memory: "));
        memorySelect = new JComboBox<>(new String[]{"512","1024","1536","2048"});
        memoryPanel.add(memorySelect);
        memoryPanel.add(new JLabel("MB"));
        memory.add(memoryPanel, BorderLayout.CENTER);
        main.add(memory, BorderLayout.CENTER);
        
        LauncherPanel opti = new LauncherPanel();
        opti.setLayout(new BorderLayout());
        opti.add(new JLabel("Game"), BorderLayout.NORTH);
        LauncherPanel optiPanel = new LauncherPanel();
        optiPanel.setLayout(new FlowLayout());
        optiPanel.add(new JLabel("Optifine edition: "));
        optiSelect = new JComboBox<>(new String[]{"standard","light","ultra"});
        optiPanel.add(optiSelect);
        opti.add(optiPanel, BorderLayout.CENTER);
        main.add(opti, BorderLayout.SOUTH);
        
        loadSetting();
        
        this.setLayout(new BorderLayout());
        this.add(main, BorderLayout.CENTER);
        
        LauncherPanel buttons = new LauncherPanel();
        buttons.setLayout(new FlowLayout());
        JButton ok = new JButton("Save");
        ok.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                save();
                close();
            }
        });
        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        buttons.add(ok);
        buttons.add(close);
        this.add(buttons, BorderLayout.SOUTH);
        
        this.pack();
        this.setVisible(true);
    }
    
    private void loadSetting(){
        OptionManager om = controller.getAccount().getOption();
        x.setText(om.getX()+"");
        y.setText(om.getY()+"");
        full.setSelected(om.isFullscreen());
        memorySelect.setSelectedItem(om.getMemory());
        optiSelect.setSelectedItem(om.getOptifineVersion());
        
    }
    
    private void save(){
        OptionManager om = controller.getAccount().getOption();
        om.setX(Integer.parseInt(x.getText()));
        om.setY(Integer.parseInt(y.getText()));
        om.setFullscreen(full.isSelected());
        om.setMemory((String)memorySelect.getSelectedItem());
        om.setOptifineVersion((String)optiSelect.getSelectedItem());
        
        controller.saveSetting();
        
    }
    
    private void close(){
        this.setVisible(false);
    }
    
}
