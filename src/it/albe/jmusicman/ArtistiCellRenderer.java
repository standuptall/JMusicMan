

package it.albe.jmusicman;
import java.awt.Color;
import java.awt.Image;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

public class ArtistiCellRenderer implements TreeCellRenderer {
  JLabel imgLabel = new JLabel();
  

  JLabel nomeLabel = new JLabel(" ");

  JPanel renderer = new JPanel();

  DefaultTreeCellRenderer defaultRenderer = new DefaultTreeCellRenderer();

  Color backgroundSelectionColor;

  Color backgroundNonSelectionColor;

  public ArtistiCellRenderer() {
    renderer.add(imgLabel);
    nomeLabel.setForeground(Style.foreGroundColor);
    nomeLabel.setFont(new java.awt.Font("Arial", 0, 14));
    renderer.add(nomeLabel);
    nomeLabel.setHorizontalAlignment(JLabel.RIGHT);
    nomeLabel.setForeground(Style.backGroundColor);
    //renderer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    backgroundSelectionColor = defaultRenderer.getBackgroundSelectionColor();
    backgroundNonSelectionColor = defaultRenderer.getBackgroundNonSelectionColor();
  }

  public java.awt.Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
    boolean expanded, boolean leaf, int row, boolean hasFocus) {
    java.awt.Component returnValue = null;
    if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
      Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
      if (userObject instanceof it.albe.jmusicman.Album) {
        it.albe.jmusicman.Album album = (it.albe.jmusicman.Album) userObject;
        nomeLabel.setText(album.toString());
        nomeLabel.setForeground(Style.foreGroundColor);
        final String resourcesPath = "icons/album.png";
        ImageIcon img = new ImageIcon(it.albe.jmusicman.JMusicMan.class.getResource(resourcesPath));
        Image imgscale = img.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_DEFAULT);
        img = new ImageIcon(imgscale);
        imgLabel.setIcon(img);
        if (selected) {
          renderer.setBackground(backgroundSelectionColor);
        } else {
          renderer.setBackground(Style.backGroundColor);
        }
        renderer.setEnabled(tree.isEnabled());
        returnValue = renderer;
      } else if (userObject instanceof String) {
        String artist = (String) userObject;
        nomeLabel.setText(artist);
        nomeLabel.setForeground(Style.foreGroundColor);
        final String resourcesPath = "icons/artist.png";
        ImageIcon img = new ImageIcon(it.albe.jmusicman.JMusicMan.class.getResource(resourcesPath));
        Image imgscale = img.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_DEFAULT);
        img = new ImageIcon(imgscale);
        imgLabel.setIcon(img);
        if (selected) {
          renderer.setBackground(backgroundSelectionColor);
        } else {
          renderer.setBackground(Style.backGroundColor);
        }
        renderer.setEnabled(tree.isEnabled());
        returnValue = renderer;
      }
      else {
          
      }
    }
    if (returnValue == null) {
      returnValue = defaultRenderer.getTreeCellRendererComponent(tree, value, selected, expanded,
          leaf, row, hasFocus);
    }
    return returnValue;
  }
}