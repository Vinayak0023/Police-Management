package com.police.test;

import com.police.data.DatabaseManager;
import com.police.model.Criminal;
import com.police.ui.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class CaptureScreenshots {
    public static void main(String[] args) {
        System.out.println(">>> Starting Automated Screen Capture Utility for Crime Management System...");

        File outDir = new File("screenshots");
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setSize(1180, 800);
                frame.addNotify();
                frame.validate();
                Container content = frame.getContentPane();
                content.setSize(1180, 800);
                content.doLayout();
                content.validate();

                // 1. Capture Page 1: Search / Criminal Records Directory
                frame.showPage1Search();
                content.doLayout();
                content.validate();
                renderAndSave(content, new File(outDir, "screenshot_swing_search.png"));
                System.out.println("Saved: screenshots/screenshot_swing_search.png");

                // 2. Capture Page 2: Criminal Dossier with 3-Angle Mugshots
                Criminal criminal = DatabaseManager.getInstance().getCriminalById("RCB-2024-089");
                if (criminal != null) {
                    frame.showPage2CriminalDetail(criminal);
                    content.doLayout();
                    content.validate();
                    renderAndSave(content, new File(outDir, "screenshot_swing_detail.png"));
                    System.out.println("Saved: screenshots/screenshot_swing_detail.png");
                }

                // 3. Capture Page 3: Add Criminal Form
                frame.showPage3AddCriminal();
                content.doLayout();
                content.validate();
                renderAndSave(content, new File(outDir, "screenshot_swing_add.png"));
                System.out.println("Saved: screenshots/screenshot_swing_add.png");

                // 4. Capture Page 4: FIR Management & Investigation Tracking
                frame.showPage4FIR();
                content.doLayout();
                content.validate();
                renderAndSave(content, new File(outDir, "screenshot_swing_fir.png"));
                System.out.println("Saved: screenshots/screenshot_swing_fir.png");

                System.out.println(">>> ALL SWING SCREENSHOTS CAPTURED SUCCESSFULLY! 📸");
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        });
    }

    private static void renderAndSave(Container comp, File file) throws Exception {
        int w = comp.getWidth();
        int h = comp.getHeight();
        if (w <= 0) w = 1180;
        if (h <= 0) h = 800;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        comp.printAll(g2); // printAll forces all children to paint
        g2.dispose();
        ImageIO.write(image, "PNG", file);
    }
}
