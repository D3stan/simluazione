package a02b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;


public class GUI extends JFrame {
    
    private final Map<Point, JButton> cells = new HashMap<>();
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        final Logic logic = new LogicImpl(size);
        final JPanel main = new JPanel(new BorderLayout());
        final JPanel panel = new JPanel(new GridLayout(size,size));
        final JButton checkButton = new JButton("Check > Restart");
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        main.add(BorderLayout.SOUTH, checkButton);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
                button.setText(logic.set(cells.entrySet()
                    .stream()
                    .filter((entry) -> entry.getValue().equals(button))
                    .findAny()
                    .get().getKey())
                );
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(new Point(j, i), jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<Point, Boolean> toCheck = logic.check();
                cells.forEach((point, button) -> {
                    if (toCheck.containsKey(point)) {
                        button.setEnabled(toCheck.get(point));
                        button.setText(toCheck.get(point) ? "" : "*");
                    } else {
                        button.setText("");
                    }
                });
            }
            
        });
        this.setVisible(true);
    }    
}
