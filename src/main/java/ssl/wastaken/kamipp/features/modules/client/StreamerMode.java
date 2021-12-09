package ssl.wastaken.kamipp.features.modules.client;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.PotionEffect;
import ssl.wastaken.kamipp.Kami;
import ssl.wastaken.kamipp.features.modules.Module;
import ssl.wastaken.kamipp.features.setting.Setting;
import ssl.wastaken.kamipp.util.EntityUtil;
import ssl.wastaken.kamipp.util.TextUtil;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public
class StreamerMode
        extends Module {
    public Setting< Integer > width = this.register ( new Setting <> ( "Width" , 600 , 100 , 3160 ) );
    public Setting < Integer > height = this.register ( new Setting <> ( "Height" , 900 , 100 , 2140 ) );
    private SecondScreenFrame window;

    public
    StreamerMode ( ) {
        super ( "StreamerMode" , "Displays client info in a second window." , Module.Category.CLIENT , false , false , false );
    }

    @Override
    public
    void onEnable ( ) {
        EventQueue.invokeLater ( ( ) -> {
            if ( this.window == null ) {
                this.window = new SecondScreenFrame ( );
            }
            this.window.setVisible ( true );
        } );
    }

    @Override
    public
    void onDisable ( ) {
        if ( this.window != null ) {
            this.window.setVisible ( false );
        }
        this.window = null;
    }

    @Override
    public
    void onLogout ( ) {
        if ( this.window != null ) {
            ArrayList < String > drawInfo = new ArrayList <> ( );
            drawInfo.add ( "Phobos v1.9.0" );
            drawInfo.add ( "" );
            drawInfo.add ( "No Connection." );
            this.window.setToDraw ( drawInfo );
        }
    }

    @Override
    public
    void onUnload ( ) {
        this.disable ( );
    }

    @Override
    public
    void onLoad ( ) {
        this.disable ( );
    }

    @Override
    public
    void onUpdate ( ) {
        if ( this.window != null ) {
            ArrayList < String > drawInfo = new ArrayList <> ( );
            drawInfo.add ( "Kami++ v1.0" );
            drawInfo.add ( "" );
            drawInfo.add ( "Fps: " + Minecraft.debugFPS );
            drawInfo.add ( "TPS: " + Kami.serverManager.getTPS ( ) );
            drawInfo.add ( "Ping: " + Kami.serverManager.getPing ( ) + "ms" );
            drawInfo.add ( "Speed: " + Kami.speedManager.getSpeedKpH ( ) + "km/h" );
            drawInfo.add ( "Time: " + new SimpleDateFormat ( "h:mm a" ).format ( new Date ( ) ) );
            boolean inHell = StreamerMode.mc.world.getBiome ( StreamerMode.mc.player.getPosition ( ) ).getBiomeName ( ).equals ( "Hell" );
            int posX = (int) StreamerMode.mc.player.posX;
            int posY = (int) StreamerMode.mc.player.posY;
            int posZ = (int) StreamerMode.mc.player.posZ;
            float nether = ! inHell ? 0.125f : 8.0f;
            int hposX = (int) ( StreamerMode.mc.player.posX * (double) nether );
            int hposZ = (int) ( StreamerMode.mc.player.posZ * (double) nether );
            String coordinates = "XYZ " + posX + ", " + posY + ", " + posZ + " [" + hposX + ", " + hposZ + "]";
            String text = Kami.rotationManager.getDirection4D ( false );
            drawInfo.add ( "" );
            drawInfo.add ( text );
            drawInfo.add ( coordinates );
            drawInfo.add ( "" );
            for (Module module : Kami.moduleManager.sortedModules) {
                String moduleName = TextUtil.stripColor ( module.getFullArrayString ( ) );
                drawInfo.add ( moduleName );
            }
            drawInfo.add ( "" );
            for (PotionEffect effect : Kami.potionManager.getOwnPotions ( )) {
                String potionText = TextUtil.stripColor ( Kami.potionManager.getColoredPotionString ( effect ) );
                drawInfo.add ( potionText );
            }
            drawInfo.add ( "" );
            Map < String, Integer > map = EntityUtil.getTextRadarPlayers ( );
            if ( ! map.isEmpty ( ) ) {
                for (Map.Entry < String, Integer > player : map.entrySet ( )) {
                    String playerText = TextUtil.stripColor ( player.getKey ( ) );
                    drawInfo.add ( playerText );
                }
            }
            this.window.setToDraw ( drawInfo );
        }
    }

    public
    class SecondScreen
            extends JPanel {
        private final int B_WIDTH;
        private final int B_HEIGHT;
        private Font font;
        private ArrayList < String > toDraw;

        public
        SecondScreen ( ) {
            this.B_WIDTH = StreamerMode.this.width.getValue ( );
            this.B_HEIGHT = StreamerMode.this.height.getValue ( );
            this.toDraw = new ArrayList <> ( );
            this.initBoard ( );
        }

        public
        void setToDraw ( ArrayList < String > list ) {
            this.toDraw = list;
            this.repaint ( );
        }

        public
        void setWindowSize ( int width , int height ) {
            this.setPreferredSize ( new Dimension ( width , height ) );
        }

        private
        void initBoard ( ) {
            this.setBackground ( Color.black );
            this.setFocusable ( true );
            this.setPreferredSize ( new Dimension ( this.B_WIDTH , this.B_HEIGHT ) );
        }

        @Override
        public
        void paintComponent ( Graphics g ) {
            super.paintComponent ( g );
            this.drawScreen ( g );
        }

        private
        void drawScreen ( Graphics g ) {
            Font small = this.font;
            g.setColor ( Color.white );
            int y = 40;
            for (String msg : this.toDraw) {
                y += 20;
            }
            Toolkit.getDefaultToolkit ( ).sync ( );
        }
    }

    public
    class SecondScreenFrame
            extends JFrame {
        private SecondScreen panel;

        public
        SecondScreenFrame ( ) {
            this.initUI ( );
        }

        private
        void initUI ( ) {
            this.panel = new SecondScreen ( );
            this.add ( this.panel );
            this.setResizable ( true );
            this.pack ( );
            this.setTitle ( "Phobos - Info" );
            this.setLocationRelativeTo ( null );
            this.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
        }

        public
        void setToDraw ( ArrayList < String > list ) {
            this.panel.setToDraw ( list );
        }
    }
}
