/*
 * Decompiled with CFR 0_115.
 */
import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Slime2P
extends Applet
implements Runnable {
    private int nWidth;
    private int nHeight;
    private final int topScore = 10;
    private int nScore;
    private int nPointsScored;
    private int p1X;
    private int p2X;
    private int p1Y;
    private int p2Y;
    private int p1Col;
    private int p2Col = 1;
    private Color[] slimeColours = new Color[]{Color.red, Color.green, Color.yellow, Color.white, Color.black};
    private String[] slimeColText = new String[]{"Big Red Slime ", "Magic Green Slime ", "Golden Boy ", "The Great White Slime ", "The Grass Tree\u00a9 "};
    private int p1OldX;
    private int p2OldX;
    private int p1OldY;
    private int p2OldY;
    private int p1XV;
    private int p2XV;
    private int p1YV;
    private int p2YV;
    private int ballX;
    private int ballY;
    private int ballVX;
    private int ballVY;
    private int ballOldX;
    private int ballOldY;
    private Graphics screen;
    private String promptMsg;
    private int[][] replayData = new int[200][8];
    private int replayPos;
    private int replayStart;
    private boolean mousePressed;
    private boolean fCanChangeCol;
    private boolean fInPlay;
    private int p1Blink;
    private int p2Blink;
    private boolean fP1Touched;
    private boolean fP2Touched;
    private Thread gameThread;
    private boolean fEndGame;
    private long startTime;
    private long gameTime;
    private int scoringRun;
    private int frenzyCol;
    private final int scoringRunForSuper = 3;

    /*
     * Exception decompiling
     */
    public boolean handleEvent(Event var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:486)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.rebuildSwitches(SwitchReplacer.java:334)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:539)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doClass(Main.java:46)
        // org.benf.cfr.reader.Main.main(Main.java:183)
        throw new IllegalStateException("Decompilation failed");
    }

    private void DrawSlimers() {
        Color color;
        Color color2;
        boolean bl;
        int n = this.nWidth / 10;
        int n2 = this.nHeight / 10;
        int n3 = this.nWidth / 50;
        int n4 = this.nHeight / 25;
        int n5 = this.ballX * this.nWidth / 1000;
        int n6 = 4 * this.nHeight / 5 - this.ballY * this.nHeight / 1000;
        int n7 = this.p1OldX * this.nWidth / 1000 - n / 2;
        int n8 = 7 * this.nHeight / 10 - this.p1OldY * this.nHeight / 1000;
        this.screen.setColor(Color.blue);
        this.screen.fillRect(n7, n8, n, n2);
        n7 = this.p2OldX * this.nWidth / 1000 - n / 2;
        n8 = 7 * this.nHeight / 10 - this.p2OldY * this.nHeight / 1000;
        this.screen.setColor(Color.blue);
        this.screen.fillRect(n7, n8, n, n2);
        this.MoveBall();
        n7 = this.p1X * this.nWidth / 1000 - n / 2;
        n8 = 7 * this.nHeight / 10 - this.p1Y * this.nHeight / 1000;
        if (this.scoringRun <= -3) {
            this.frenzyCol = (this.frenzyCol + 1) % this.slimeColours.length;
            color2 = this.slimeColours[this.frenzyCol];
        } else {
            color2 = this.slimeColours[this.p1Col];
        }
        this.screen.setColor(color2);
        this.screen.fillArc(n7, n8, n, 2 * n2, 0, 180);
        int n9 = this.p1X + 38;
        int n10 = this.p1Y - 60;
        n7 = n9 * this.nWidth / 1000;
        n8 = 7 * this.nHeight / 10 - n10 * this.nHeight / 1000;
        int n11 = n7 - n5;
        int n12 = n8 - n6;
        int n13 = (int)Math.sqrt(n11 * n11 + n12 * n12);
        boolean bl2 = bl = Math.random() < 0.01;
        if (bl) {
            this.p1Blink = 5;
        }
        if (this.p1Blink == 0) {
            this.screen.setColor(Color.white);
            this.screen.fillOval(n7 - n3, n8 - n4, n3, n4);
            if (n13 > 0 && !bl) {
                this.screen.setColor(Color.black);
                this.screen.fillOval(n7 - 4 * n11 / n13 - 3 * n3 / 4, n8 - 4 * n12 / n13 - 3 * n4 / 4, n3 / 2, n4 / 2);
            }
        } else {
            --this.p1Blink;
        }
        n7 = this.p2X * this.nWidth / 1000 - n / 2;
        n8 = 7 * this.nHeight / 10 - this.p2Y * this.nHeight / 1000;
        if (this.scoringRun >= 3) {
            this.frenzyCol = (this.frenzyCol + 1) % this.slimeColours.length;
            color = this.slimeColours[this.frenzyCol];
        } else {
            color = this.slimeColours[this.p2Col];
        }
        this.screen.setColor(color);
        this.screen.fillArc(n7, n8, n, 2 * n2, 0, 180);
        n9 = this.p2X - 18;
        n10 = this.p2Y - 60;
        n7 = n9 * this.nWidth / 1000;
        n8 = 7 * this.nHeight / 10 - n10 * this.nHeight / 1000;
        n11 = n7 - n5;
        n12 = n8 - n6;
        n13 = (int)Math.sqrt(n11 * n11 + n12 * n12);
        boolean bl3 = bl = Math.random() < 0.01;
        if (bl) {
            this.p2Blink = 5;
        }
        if (this.p2Blink == 0) {
            this.screen.setColor(bl ? Color.gray : Color.white);
            this.screen.fillOval(n7 - n3, n8 - n4, n3, n4);
            if (n13 > 0 && !bl) {
                this.screen.setColor(Color.black);
                this.screen.fillOval(n7 - 4 * n11 / n13 - 3 * n3 / 4, n8 - 4 * n12 / n13 - 3 * n4 / 4, n3 / 2, n4 / 2);
            }
        } else {
            --this.p2Blink;
        }
        if (this.nScore > 8) {
            int n14 = this.p1X * this.nWidth / 1000;
            int n15 = 7 * this.nHeight / 10 - (this.p1Y - 40) * this.nHeight / 1000;
            int n16 = this.nWidth / 20;
            int n17 = this.nHeight / 20;
            int n18 = 0;
            do {
                this.screen.setColor(Color.black);
                this.screen.drawArc(n14, n15 + n18, n16, n17, -30, -150);
            } while (++n18 < 3);
            return;
        }
        if (this.nScore < 2) {
            int n19 = this.nWidth / 20;
            int n20 = this.nHeight / 20;
            int n21 = this.p2X * this.nWidth / 1000 - n19;
            int n22 = 7 * this.nHeight / 10 - (this.p2Y - 40) * this.nHeight / 1000;
            int n23 = 0;
            do {
                this.screen.setColor(Color.black);
                this.screen.drawArc(n21, n22 + n23, n19, n20, -10, -150);
            } while (++n23 < 3);
        }
    }

    public void paint(Graphics graphics) {
        this.nWidth = this.size().width;
        this.nHeight = this.size().height;
        graphics.setColor(Color.blue);
        graphics.fillRect(0, 0, this.nWidth, 4 * this.nHeight / 5);
        graphics.setColor(Color.gray);
        graphics.fillRect(0, 4 * this.nHeight / 5, this.nWidth, this.nHeight / 5);
        graphics.setColor(Color.white);
        graphics.fillRect(this.nWidth / 2 - 2, 7 * this.nHeight / 10, 4, this.nHeight / 10 + 5);
        this.drawScores();
        this.drawPrompt();
        if (!this.fInPlay) {
            FontMetrics fontMetrics = this.screen.getFontMetrics();
            this.screen.setColor(Color.white);
            this.screen.drawString("Slime Volleyball!", this.nWidth / 2 - fontMetrics.stringWidth("Slime Volleyball!") / 2, this.nHeight / 2 - fontMetrics.getHeight());
            graphics.setColor(Color.white);
            fontMetrics = graphics.getFontMetrics();
            graphics.drawString("Written by Quin Pendragon", this.nWidth / 2 - fontMetrics.stringWidth("Written by Quin Pendragon") / 2, this.nHeight / 2 + fontMetrics.getHeight() * 2);
        }
    }

    public void destroy() {
        this.gameThread.stop();
        this.gameThread = null;
    }

    private void ReplayFrame(int n, int n2, int n3, int n4, int n5, boolean bl) {
        if (bl) {
            this.ballOldX = -50000000;
            this.ballX = -50000000;
            this.ballOldY = 100000;
            this.ballY = 100000;
            this.p2OldY = -10000;
            this.p2OldX = -10000;
            this.p1OldY = -10000;
            this.p1OldX = -10000;
        } else {
            int n6 = n != 0 ? n - 1 : 199;
            this.p1OldX = this.replayData[n6][0];
            this.p1OldY = this.replayData[n6][1];
            this.p2OldX = this.replayData[n6][2];
            this.p2OldY = this.replayData[n6][3];
            this.ballOldX = this.replayData[n6][4];
            this.ballOldY = this.replayData[n6][5];
        }
        this.p1X = this.replayData[n][0];
        this.p1Y = this.replayData[n][1];
        this.p2X = this.replayData[n][2];
        this.p2Y = this.replayData[n][3];
        this.ballX = this.replayData[n][4];
        this.ballY = this.replayData[n][5];
        this.p1Col = this.replayData[n][6];
        this.p2Col = this.replayData[n][7];
        this.ballVX = 0;
        this.ballVY = 1;
        if (n / 10 % 2 > 0) {
            this.screen.setColor(Color.red);
            this.screen.drawString("Replay...", n2, n3);
        } else {
            this.screen.setColor(Color.blue);
            this.screen.fillRect(n2, n3 - n5, n4, n5 * 2);
        }
        this.DrawSlimers();
        try {
            Thread.sleep(20);
            return;
        }
        catch (InterruptedException v0) {
            return;
        }
    }

    private String MakeTime(long l) {
        long l2 = l / 10 % 100;
        long l3 = l / 1000 % 60;
        long l4 = l / 60000 % 60;
        long l5 = l / 3600000;
        String string = "";
        if (l5 < 10) {
            string = String.valueOf(string) + "0";
        }
        string = String.valueOf(string) + l5;
        string = String.valueOf(string) + ":";
        if (l4 < 10) {
            string = String.valueOf(string) + "0";
        }
        string = String.valueOf(string) + l4;
        string = String.valueOf(string) + ":";
        if (l3 < 10) {
            string = String.valueOf(string) + "0";
        }
        string = String.valueOf(string) + l3;
        string = String.valueOf(string) + ":";
        if (l2 < 10) {
            string = String.valueOf(string) + "0";
        }
        string = String.valueOf(string) + l2;
        return string;
    }

    private void MoveSlimers() {
        this.p1X += this.p1XV;
        if (this.p1X < 50) {
            this.p1X = 50;
        }
        if (this.p1X > 445) {
            this.p1X = 445;
        }
        if (this.p1YV != 0) {
            this.p1Y += (this.p1YV -= this.scoringRun <= -3 ? 4 : 2);
            if (this.p1Y < 0) {
                this.p1Y = 0;
                this.p1YV = 0;
            }
        }
        this.p2X += this.p2XV;
        if (this.p2X > 950) {
            this.p2X = 950;
        }
        if (this.p2X < 555) {
            this.p2X = 555;
        }
        if (this.p2YV != 0) {
            this.p2Y += (this.p2YV -= this.scoringRun >= 3 ? 4 : 2);
            if (this.p2Y < 0) {
                this.p2Y = 0;
                this.p2YV = 0;
            }
        }
    }

    private void MoveBall() {
        int n = 30 * this.nHeight / 1000;
        int n2 = this.ballOldX * this.nWidth / 1000;
        int n3 = 4 * this.nHeight / 5 - this.ballOldY * this.nHeight / 1000;
        this.screen.setColor(Color.blue);
        this.screen.fillOval(n2 - n, n3 - n, n * 2, n * 2);
        this.ballY += --this.ballVY;
        this.ballX += this.ballVX;
        if (!this.fEndGame) {
            int n4;
            int n5;
            int n6 = (this.ballX - this.p1X) * 2;
            int n7 = this.ballY - this.p1Y;
            int n8 = n6 * n6 + n7 * n7;
            int n9 = this.ballVX - this.p1XV;
            int n10 = this.ballVY - this.p1YV;
            if (n7 > 0 && n8 < 15625 && n8 > 25) {
                n4 = (int)Math.sqrt(n8);
                n5 = (n6 * n9 + n7 * n10) / n4;
                this.ballX = this.p1X + n6 * 63 / n4;
                this.ballY = this.p1Y + n7 * 125 / n4;
                if (n5 <= 0) {
                    this.ballVX += this.p1XV - 2 * n6 * n5 / n4;
                    if (this.ballVX < -15) {
                        this.ballVX = -15;
                    }
                    if (this.ballVX > 15) {
                        this.ballVX = 15;
                    }
                    this.ballVY += this.p1YV - 2 * n7 * n5 / n4;
                    if (this.ballVY < -22) {
                        this.ballVY = -22;
                    }
                    if (this.ballVY > 22) {
                        this.ballVY = 22;
                    }
                }
                this.fP1Touched = true;
            }
            n6 = (this.ballX - this.p2X) * 2;
            n7 = this.ballY - this.p2Y;
            n8 = n6 * n6 + n7 * n7;
            n9 = this.ballVX - this.p2XV;
            n10 = this.ballVY - this.p2YV;
            if (n7 > 0 && n8 < 15625 && n8 > 25) {
                n4 = (int)Math.sqrt(n8);
                n5 = (n6 * n9 + n7 * n10) / n4;
                this.ballX = this.p2X + n6 * 63 / n4;
                this.ballY = this.p2Y + n7 * 125 / n4;
                if (n5 <= 0) {
                    this.ballVX += this.p2XV - 2 * n6 * n5 / n4;
                    if (this.ballVX < -15) {
                        this.ballVX = -15;
                    }
                    if (this.ballVX > 15) {
                        this.ballVX = 15;
                    }
                    this.ballVY += this.p2YV - 2 * n7 * n5 / n4;
                    if (this.ballVY < -22) {
                        this.ballVY = -22;
                    }
                    if (this.ballVY > 22) {
                        this.ballVY = 22;
                    }
                }
                this.fP2Touched = true;
            }
            if (this.ballX < 15) {
                this.ballX = 15;
                this.ballVX = - this.ballVX;
            }
            if (this.ballX > 985) {
                this.ballX = 985;
                this.ballVX = - this.ballVX;
            }
            if (this.ballX > 480 && this.ballX < 520 && this.ballY < 140) {
                if (this.ballVY < 0 && this.ballY > 130) {
                    this.ballVY *= -1;
                    this.ballY = 130;
                } else if (this.ballX < 500) {
                    this.ballX = 480;
                    this.ballVX = this.ballVX >= 0 ? - this.ballVX : this.ballVX;
                } else {
                    this.ballX = 520;
                    this.ballVX = this.ballVX <= 0 ? - this.ballVX : this.ballVX;
                }
            }
        }
        n2 = this.ballX * this.nWidth / 1000;
        n3 = 4 * this.nHeight / 5 - this.ballY * this.nHeight / 1000;
        this.screen.setColor(Color.yellow);
        this.screen.fillOval(n2 - n, n3 - n, n * 2, n * 2);
    }

    private void DrawStatus() {
        Graphics graphics = this.screen;
        int n = this.nHeight / 20;
        graphics.setColor(Color.blue);
        FontMetrics fontMetrics = this.screen.getFontMetrics();
        int n2 = this.nWidth / 2 + (this.nScore - 5) * this.nWidth / 24;
        String string = "Points: " + this.nPointsScored + "   Elapsed: " + this.MakeTime(this.gameTime);
        int n3 = fontMetrics.stringWidth(string);
        graphics.fillRect(n2 - n3 / 2 - 5, 0, n3 + 10, n + 22);
        graphics.setColor(Color.white);
        this.screen.drawString(string, n2 - n3 / 2, fontMetrics.getAscent() + 20);
    }

    public void drawPrompt() {
        this.screen.setColor(Color.gray);
        this.screen.fillRect(0, 4 * this.nHeight / 5 + 6, this.nWidth, this.nHeight / 5 - 10);
        this.drawPrompt(this.promptMsg, 0);
    }

    public void drawPrompt(String string, int n) {
        FontMetrics fontMetrics = this.screen.getFontMetrics();
        this.screen.setColor(Color.lightGray);
        this.screen.drawString(string, (this.nWidth - fontMetrics.stringWidth(string)) / 2, this.nHeight * 4 / 5 + fontMetrics.getHeight() * (n + 1) + 10);
    }

    private void SaveReplayData() {
        this.replayData[this.replayPos][0] = this.p1X;
        this.replayData[this.replayPos][1] = this.p1Y;
        this.replayData[this.replayPos][2] = this.p2X;
        this.replayData[this.replayPos][3] = this.p2Y;
        this.replayData[this.replayPos][4] = this.ballX;
        this.replayData[this.replayPos][5] = this.ballY;
        this.replayData[this.replayPos][6] = this.p1Col;
        this.replayData[this.replayPos][7] = this.p2Col;
        ++this.replayPos;
        if (this.replayPos >= 200) {
            this.replayPos = 0;
        }
        if (this.replayStart == this.replayPos) {
            ++this.replayStart;
        }
        if (this.replayStart >= 200) {
            this.replayStart = 0;
        }
    }

    private void drawScores() {
        int n;
        Graphics graphics = this.screen;
        int n2 = this.nHeight / 20;
        graphics.setColor(Color.blue);
        graphics.fillRect(0, 0, this.nWidth, n2 + 22);
        int n3 = 0;
        while (n3 < this.nScore) {
            n = (n3 + 1) * this.nWidth / 24;
            graphics.setColor(this.slimeColours[this.p1Col]);
            graphics.fillOval(n, 20, n2, n2);
            graphics.setColor(Color.white);
            graphics.drawOval(n, 20, n2, n2);
            ++n3;
        }
        n = 0;
        while (n < 10 - this.nScore) {
            int n4 = this.nWidth - (n + 1) * this.nWidth / 24 - n2;
            graphics.setColor(this.slimeColours[this.p2Col]);
            graphics.fillOval(n4, 20, n2, n2);
            graphics.setColor(Color.white);
            graphics.drawOval(n4, 20, n2, n2);
            ++n;
        }
    }

    public void run() {
        this.replayStart = 0;
        this.replayPos = 0;
        this.p1Col = 0;
        this.p2Col = 1;
        this.scoringRun = 0;
        this.fP2Touched = false;
        this.fP1Touched = false;
        this.nPointsScored = 0;
        this.startTime = System.currentTimeMillis();
        while (this.nScore != 0 && this.nScore != 10 && this.gameThread != null) {
            this.gameTime = System.currentTimeMillis() - this.startTime;
            this.SaveReplayData();
            this.p1OldX = this.p1X;
            this.p1OldY = this.p1Y;
            this.p2OldX = this.p2X;
            this.p2OldY = this.p2Y;
            this.ballOldX = this.ballX;
            this.ballOldY = this.ballY;
            this.MoveSlimers();
            this.DrawSlimers();
            this.DrawStatus();
            if (this.ballY < 35) {
                int n;
                boolean bl;
                long l;
                l = System.currentTimeMillis();
                ++this.nPointsScored;
                this.nScore += this.ballX <= 500 ? -1 : 1;
                if (this.ballX <= 500 && this.scoringRun >= 0) {
                    ++this.scoringRun;
                } else if (this.ballX > 500 && this.scoringRun <= 0) {
                    --this.scoringRun;
                } else if (this.ballX <= 500 && this.scoringRun <= 0) {
                    this.scoringRun = 1;
                } else if (this.ballX > 500 && this.scoringRun >= 0) {
                    this.scoringRun = -1;
                }
                String string = this.promptMsg = this.ballX <= 500 ? this.slimeColText[this.p2Col] : this.slimeColText[this.p1Col];
                if (!this.fP1Touched && !this.fP2Touched) {
                    this.promptMsg = "What can I say?";
                } else if ((this.scoringRun < 0 ? - this.scoringRun : this.scoringRun) == 3) {
                    this.promptMsg = String.valueOf(this.promptMsg) + "is on fire!";
                } else if (this.ballX > 500 && this.fP1Touched && !this.fP2Touched || this.ballX <= 500 && !this.fP1Touched && this.fP2Touched) {
                    this.promptMsg = String.valueOf(this.promptMsg) + "aces the serve!";
                } else if (this.ballX > 500 && !this.fP1Touched && this.fP2Touched || this.ballX <= 500 && this.fP1Touched && !this.fP2Touched) {
                    this.promptMsg = String.valueOf(this.promptMsg) + "dies laughing! :P";
                } else {
                    switch (this.nScore) {
                        case 0: 
                        case 10: {
                            if (this.nPointsScored == 5) {
                                this.promptMsg = String.valueOf(this.promptMsg) + "Wins with a QUICK FIVE!!!";
                                break;
                            }
                            if (this.scoringRun == 8) {
                                this.promptMsg = String.valueOf(this.promptMsg) + "Wins with a BIG NINE!!!";
                                break;
                            }
                            this.promptMsg = String.valueOf(this.promptMsg) + "Wins!!!";
                            break;
                        }
                        case 4: {
                            this.promptMsg = String.valueOf(this.promptMsg) + (this.ballX >= 500 ? "Scores!" : "takes the lead!!");
                            break;
                        }
                        case 6: {
                            this.promptMsg = String.valueOf(this.promptMsg) + (this.ballX <= 500 ? "Scores!" : "takes the lead!!");
                            break;
                        }
                        case 5: {
                            this.promptMsg = String.valueOf(this.promptMsg) + "Equalizes!";
                            break;
                        }
                        default: {
                            this.promptMsg = String.valueOf(this.promptMsg) + "Scores!";
                            break;
                        }
                    }
                }
                this.fCanChangeCol = false;
                bl = this.nScore != 0 && this.nScore != 10;
                n = this.ballX;
                this.drawPrompt();
                if (bl) {
                    this.drawPrompt("Click mouse for replay...", 1);
                    this.mousePressed = false;
                    if (this.gameThread != null) {
                        try {
                            Thread.sleep(2500);
                        }
                        catch (InterruptedException v1) {}
                    }
                    if (this.mousePressed) {
                        this.SaveReplayData();
                        this.DoReplay();
                    }
                } else if (this.gameThread != null) {
                    try {
                        Thread.sleep(2500);
                    }
                    catch (InterruptedException v2) {}
                }
                this.promptMsg = "";
                this.drawPrompt();
                this.fCanChangeCol = true;
                if (bl) {
                    this.p1X = 200;
                    this.p1Y = 0;
                    this.p2X = 800;
                    this.p2Y = 0;
                    this.p1XV = 0;
                    this.p1YV = 0;
                    this.p2XV = 0;
                    this.p2YV = 0;
                    this.ballX = n >= 500 ? 200 : 800;
                    this.ballY = 400;
                    this.ballVX = 0;
                    this.ballVY = 0;
                    this.replayPos = 0;
                    this.replayStart = 0;
                    this.fP2Touched = false;
                    this.fP1Touched = false;
                    this.repaint();
                }
                this.startTime += System.currentTimeMillis() - l;
            }
            if (this.gameThread == null) continue;
            try {
                Thread.sleep(20);
                continue;
            }
            catch (InterruptedException v3) {}
        }
        this.fEndGame = true;
        this.SaveReplayData();
        this.DoReplay();
        this.fInPlay = false;
        this.promptMsg = "Click the mouse to play...";
        this.repaint();
    }

    public void init() {
        this.nWidth = this.size().width;
        this.nHeight = this.size().height;
        this.nScore = 5;
        this.fEndGame = false;
        this.fInPlay = false;
        this.fCanChangeCol = true;
        this.promptMsg = "Click the mouse to play...";
        this.screen = this.getGraphics();
        this.screen.setFont(new Font(this.screen.getFont().getName(), 1, 15));
    }

    private void DoReplay() {
        FontMetrics fontMetrics = this.screen.getFontMetrics();
        int n = fontMetrics.stringWidth("Replay...");
        int n2 = fontMetrics.getHeight();
        int n3 = this.nWidth / 2 - n / 2;
        int n4 = this.nHeight / 2 - n2;
        this.promptMsg = "Click the mouse to continue...";
        this.mousePressed = false;
        int n5 = this.replayPos - 1;
        while (!this.mousePressed) {
            if (++n5 >= 200) {
                n5 = 0;
            }
            if (n5 == this.replayPos) {
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException v0) {}
                n5 = this.replayStart;
                this.paint(this.getGraphics());
            }
            this.ReplayFrame(n5, n3, n4, n, n2, false);
        }
        this.promptMsg = "";
        this.paint(this.getGraphics());
    }

    private void DoFatality() {
    }
}