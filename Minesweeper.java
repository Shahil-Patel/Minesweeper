import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
public class Minesweeper extends JPanel implements AdjustmentListener,ActionListener
{
	JFrame frame;
	Timer timer;
	JPanel topPanel,scorePanel, buttonPanel,gamePanel;
	Tile minefield[][];
	JMenuBar menuBar;
	JButton resetButton;
	JMenu game, icon, controls;
	JLabel mineCounter, mineTimer;
	Icon closedTile;
	int mineCount=0;
	boolean gameEnd=false;
	int time=0;
	boolean win=false;
	boolean firstClick;
	boolean dT=true;
	boolean t1=false;
	boolean t2=false;
	int dim=0;
	String t="000";
	JMenuItem beginner, intermediate, expert, original, theme1, theme2;
	public Minesweeper()
	{
		frame=new JFrame("Minesweeper");
		frame.add(this);

		timer=new Timer();
		menuBar=new JMenuBar();
		topPanel=new JPanel(new GridLayout(2,1));
		scorePanel=new JPanel(new BorderLayout());
		buttonPanel=new JPanel();
		gamePanel=new JPanel();

		game=new JMenu("Game");
		beginner=new JMenuItem(new AbstractAction("Beginner: 9x9 grid with 10 mines") {
		    public void actionPerformed(ActionEvent e) {
				win=false;
				gameEnd=false;
				mineCount=10;
				firstClick=false;
				frame.remove(gamePanel);
				runTimer();
				dim=9;
				minefield=new Tile[dim][dim];
				gamePanel=new JPanel(new GridLayout(dim,dim));
				for(int x=0;x<minefield.length;x++)
				{
					for(int y=0;y<minefield[0].length;y++)
					{
						final int x1=x;
						final int y1=y;
						minefield[x][y]=new Tile(new JToggleButton(),0,false,false);
						if(dT)
						{
							minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("closedTile.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
						}
						else if(t1)
						{
							minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("close.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
						}
						else
						{
							minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_00.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
						}
						minefield[x][y].getButton().addMouseListener(new MouseAdapter()
						{
							public void mouseReleased(MouseEvent e)
							{
								if(e.getModifiers() == MouseEvent.BUTTON3_MASK&&!win)
								{
									if(!minefield[x1][y1].getFlagged()&&!minefield[x1][y1].getClicked())
									{
										flag(x1,y1,minefield);
										minefield[x1][y1].setFlagged(true);
										mineCount--;
									}
									else if(!minefield[x1][y1].getClicked())
									{
										if(dT)
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("closedTile.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										else if(t1)
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("close.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										else
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_00.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										minefield[x1][y1].setFlagged(false);
										mineCount++;
									}
								}
								if(e.getModifiers() == 16&&!win)
								{
									if(!firstClick&&!minefield[x1][y1].getFlagged())
									{
										runTimer();
										firstClick=true;
										minefield[x1][y1].setState(0);
										minefield[x1][y1].setClicked(true);
										if(dT)
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("empty.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										else if(t1)
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("default.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										else
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_01.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										fillGrid(x1,y1,minefield,mineCount);
										reveal(x1,y1,minefield);
									}
									else if(!minefield[x1][y1].getFlagged())
									{
										minefield[x1][y1].setClicked(true);
										spaceSetter(x1,y1,minefield[x1][y1].getState(),minefield);
										if(minefield[x1][y1].getState()==0)
										{
											reveal(x1,y1,minefield);
										}
										if(minefield[x1][y1].getState()==-1)
										{
											endGame();
										}
										win=true;
										for(int x=0;x<minefield.length;x++)
										{
											for(int y=0;y<minefield[0].length;y++)
											{
												if(minefield[x][y].getState()!=-1&&minefield[x][y].getClicked()!=true)
													win=false;
											}
										}
										if(gameEnd)
											win=false;
										if(win)
											JOptionPane.showMessageDialog(frame,"You won the game! Start a new game!","Minesweeper",JOptionPane.INFORMATION_MESSAGE);
									}
								}
							}
						});
						gamePanel.add(minefield[x][y].getButton());
					}
				}
				frame.setSize(450,500);
				frame.add(gamePanel,BorderLayout.CENTER);
				frame.revalidate();
		    }
		});
		intermediate=new JMenuItem(new AbstractAction("Intermediate: 16x16 grid with 40 mines") {
		    public void actionPerformed(ActionEvent e) {
				gameEnd=false;
				mineCount=40;
				firstClick=false;
				frame.remove(gamePanel);
				runTimer();
				dim=16;
				minefield=new Tile[dim][dim];
				gamePanel=new JPanel(new GridLayout(dim,dim));
				for(int x=0;x<minefield.length;x++)
				{
					for(int y=0;y<minefield[0].length;y++)
					{
						final int x1=x;
						final int y1=y;
						minefield[x][y]=new Tile(new JToggleButton(),0,false,false);
						if(dT)
						{
							minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("closedTile.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
						}
						else if(t1)
						{
							minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("close.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
						}
						else
						{
							minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_00.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
						}
						minefield[x][y].getButton().addMouseListener(new MouseAdapter()
						{
							public void mouseReleased(MouseEvent e)
							{
								if(e.getModifiers() == MouseEvent.BUTTON3_MASK&&!win)
								{
									if(!minefield[x1][y1].getFlagged()&&!minefield[x1][y1].getClicked())
									{
										flag(x1,y1,minefield);
										minefield[x1][y1].setFlagged(true);
										mineCount--;
									}
									else if(!minefield[x1][y1].getClicked())
									{
										if(dT)
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("closedTile.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										else if(t1)
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("close.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										else
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_00.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										minefield[x1][y1].setFlagged(false);
										mineCount++;
									}
								}
								if(e.getModifiers() == 16&&!win)
								{
									if(!firstClick&&!minefield[x1][y1].getFlagged())
									{
										runTimer();
										firstClick=true;
										minefield[x1][y1].setState(0);
										minefield[x1][y1].setClicked(true);
										if(dT)
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("empty.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										else if(t1)
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("default.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										else
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_01.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										fillGrid(x1,y1,minefield,mineCount);
										reveal(x1,y1,minefield);
									}
									else if(!minefield[x1][y1].getFlagged())
									{
										minefield[x1][y1].setClicked(true);
										spaceSetter(x1,y1,minefield[x1][y1].getState(),minefield);
										if(minefield[x1][y1].getState()==0)
										{
											reveal(x1,y1,minefield);
										}
										if(minefield[x1][y1].getState()==-1)
										{
											endGame();
										}
										win=true;
										for(int x=0;x<minefield.length;x++)
										{
											for(int y=0;y<minefield[0].length;y++)
											{
												if(minefield[x][y].getState()!=-1&&minefield[x][y].getClicked()!=true)
													win=false;
											}
										}
										if(gameEnd)
											win=false;
										if(win)
											JOptionPane.showMessageDialog(frame,"You won the game! Start a new game!","Minesweeper",JOptionPane.INFORMATION_MESSAGE);
									}
								}
							}
						});
						gamePanel.add(minefield[x][y].getButton());
					}
				}
				frame.setSize(450/9*16,500/9*16);
				frame.add(gamePanel,BorderLayout.CENTER);
				frame.revalidate();
		    }
		});
		expert=new JMenuItem(new AbstractAction("Expert: 16x30 grid with 99 mines") {
		    public void actionPerformed(ActionEvent e) {
				gameEnd=false;
				mineCount=99;
				firstClick=false;
				frame.remove(gamePanel);
				dim=30;
				runTimer();
				minefield=new Tile[dim-14][dim];
				gamePanel=new JPanel(new GridLayout(dim-14,dim));
				for(int x=0;x<minefield.length;x++)
				{
					for(int y=0;y<minefield[0].length;y++)
					{
						final int x1=x;
						final int y1=y;
						minefield[x][y]=new Tile(new JToggleButton(),0,false,false);
						if(dT)
						{
							minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("closedTile.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
						}
						else if(t1)
						{
							minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("close.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
						}
						else
						{
							minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_00.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
						}
						minefield[x][y].getButton().addMouseListener(new MouseAdapter()
						{
							public void mouseReleased(MouseEvent e)
							{
								if(e.getModifiers() == MouseEvent.BUTTON3_MASK&&!win)
								{
									if(!minefield[x1][y1].getFlagged()&&!minefield[x1][y1].getClicked())
									{
										flag(x1,y1,minefield);
										minefield[x1][y1].setFlagged(true);
										mineCount--;
									}
									else if(!minefield[x1][y1].getClicked())
									{
										if(dT)
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("closedTile.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										else if(t1)
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("close.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										else
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_00.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										minefield[x1][y1].setFlagged(false);
										mineCount++;
									}
								}
								if(e.getModifiers() == 16&&!win)
								{
									if(!firstClick&&!minefield[x1][y1].getFlagged())
									{
										runTimer();
										firstClick=true;
										minefield[x1][y1].setState(0);
										minefield[x1][y1].setClicked(true);
										if(dT)
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("empty.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										else if(t1)
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("default.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										else
										{
											minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_01.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
										}
										fillGrid(x1,y1,minefield,mineCount);
										reveal(x1,y1,minefield);
									}
									else if(!minefield[x1][y1].getFlagged())
									{
										minefield[x1][y1].setClicked(true);
										spaceSetter(x1,y1,minefield[x1][y1].getState(),minefield);
										if(minefield[x1][y1].getState()==0)
										{
											reveal(x1,y1,minefield);
										}
										if(minefield[x1][y1].getState()==-1)
										{
											endGame();
										}
										win=true;
										for(int x=0;x<minefield.length;x++)
										{
											for(int y=0;y<minefield[0].length;y++)
											{
												if(minefield[x][y].getState()!=-1&&minefield[x][y].getClicked()!=true)
													win=false;
											}
										}
										if(gameEnd)
											win=false;
										if(win)
											JOptionPane.showMessageDialog(frame,"You won the game! Start a new game!","Minesweeper",JOptionPane.INFORMATION_MESSAGE);
									}
								}
							}
						});
						gamePanel.add(minefield[x][y].getButton());
					}
				}
				frame.setSize(450/9*30,500/9*16);
				frame.add(gamePanel,BorderLayout.CENTER);
				frame.revalidate();
		    }
		});
		resetButton=new JButton(new AbstractAction("") {
		    public void actionPerformed(ActionEvent e) {
				gameEnd=false;
		        runTimer();
		        firstClick=false;
		        frame.remove(gamePanel);
		        if(dim==30)
		        {
					minefield=new Tile[dim-14][dim];
					gamePanel=new JPanel(new GridLayout(dim-14,dim));
					resetMC(99);
					for(int x=0;x<minefield.length;x++)
					{
						for(int y=0;y<minefield[0].length;y++)
						{
							final int x1=x;
							final int y1=y;
							minefield[x][y]=new Tile(new JToggleButton(),0,false,false);
							if(dT)
							{
								minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("closedTile.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
							}
							else if(t1)
							{
								minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("close.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
							}
							else
							{
								minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_00.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
							}
							minefield[x][y].getButton().addMouseListener(new MouseAdapter()
							{
								public void mouseReleased(MouseEvent e)
								{
									if(e.getModifiers() == MouseEvent.BUTTON3_MASK&&!win)
									{
										if(!minefield[x1][y1].getFlagged()&&!minefield[x1][y1].getClicked())
										{
											flag(x1,y1,minefield);
											minefield[x1][y1].setFlagged(true);
											mineCount--;
										}
										else if(!minefield[x1][y1].getClicked())
										{
											if(dT)
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("closedTile.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											else if(t1)
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("close.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											else
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_00.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											minefield[x1][y1].setFlagged(false);
											mineCount++;
										}
									}
									if(e.getModifiers() == 16&&!win)
									{
										if(!firstClick&&!minefield[x1][y1].getFlagged())
										{
											runTimer();
											firstClick=true;
											minefield[x1][y1].setState(0);
											minefield[x1][y1].setClicked(true);
											if(dT)
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("empty.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											else if(t1)
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("default.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											else
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_01.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											fillGrid(x1,y1,minefield,mineCount);
											reveal(x1,y1,minefield);
										}
										else if(!minefield[x1][y1].getFlagged())
										{
											minefield[x1][y1].setClicked(true);
											spaceSetter(x1,y1,minefield[x1][y1].getState(),minefield);
											if(minefield[x1][y1].getState()==0)
											{
												reveal(x1,y1,minefield);
											}
											if(minefield[x1][y1].getState()==-1)
											{
												endGame();
											}
											win=true;
											for(int x=0;x<minefield.length;x++)
											{
												for(int y=0;y<minefield[0].length;y++)
												{
													if(minefield[x][y].getState()!=-1&&minefield[x][y].getClicked()!=true)
														win=false;
												}
											}
											if(gameEnd)
												win=false;
											if(win)
												JOptionPane.showMessageDialog(frame,"You won the game! Start a new game!","Minesweeper",JOptionPane.INFORMATION_MESSAGE);
										}
									}
								}
							});
							gamePanel.add(minefield[x][y].getButton());
						}
					}
					frame.add(gamePanel,BorderLayout.CENTER);
					frame.revalidate();
				}
				else if(dim==16)
				{
					minefield=new Tile[dim][dim];
					gamePanel=new JPanel(new GridLayout(dim,dim));
					resetMC(40);
					for(int x=0;x<minefield.length;x++)
					{
						for(int y=0;y<minefield[0].length;y++)
						{
							final int x1=x;
							final int y1=y;
							minefield[x][y]=new Tile(new JToggleButton(),0,false,false);
							if(dT)
							{
								minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("closedTile.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
							}
							else if(t1)
							{
									minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("close.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
							}
							else
							{
								minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_00.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
							}
							minefield[x][y].getButton().addMouseListener(new MouseAdapter()
							{
								public void mouseReleased(MouseEvent e)
								{
									if(e.getModifiers() == MouseEvent.BUTTON3_MASK&&!win)
									{
										if(!minefield[x1][y1].getFlagged()&&!minefield[x1][y1].getClicked())
										{
											flag(x1,y1,minefield);
											minefield[x1][y1].setFlagged(true);
											mineCount--;
										}
										else if(!minefield[x1][y1].getClicked())
										{
											if(dT)
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("closedTile.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											else if(t1)
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("close.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											else
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_00.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											minefield[x1][y1].setFlagged(false);
											mineCount++;
										}
									}
									if(e.getModifiers() == 16&&!win)
									{
										if(!firstClick&&!minefield[x1][y1].getFlagged())
										{
											runTimer();
											firstClick=true;
											minefield[x1][y1].setState(0);
											minefield[x1][y1].setClicked(true);
											if(dT)
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("empty.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											else if(t1)
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("default.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											else
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_01.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											fillGrid(x1,y1,minefield,mineCount);
											reveal(x1,y1,minefield);
										}
										else if(!minefield[x1][y1].getFlagged())
										{
											minefield[x1][y1].setClicked(true);
											spaceSetter(x1,y1,minefield[x1][y1].getState(),minefield);
											if(minefield[x1][y1].getState()==0)
											{
												reveal(x1,y1,minefield);
											}
											if(minefield[x1][y1].getState()==-1)
											{
												endGame();
											}
											win=true;
											for(int x=0;x<minefield.length;x++)
											{
												for(int y=0;y<minefield[0].length;y++)
												{
													if(minefield[x][y].getState()!=-1&&minefield[x][y].getClicked()!=true)
														win=false;
												}
											}
											if(gameEnd)
												win=false;
											if(win)
												JOptionPane.showMessageDialog(frame,"You won the game! Start a new game!","Minesweeper",JOptionPane.INFORMATION_MESSAGE);
										}
									}
								}
							});
							gamePanel.add(minefield[x][y].getButton());gamePanel.add(minefield[x][y].getButton());
						}
					}
					frame.add(gamePanel,BorderLayout.CENTER);
					frame.revalidate();
				}
				else if(dim==9)
				{
					win=false;
					gameEnd=false;
					minefield=new Tile[dim][dim];
					gamePanel=new JPanel(new GridLayout(dim,dim));
					resetMC(10);
					frame.revalidate();
					for(int x=0;x<minefield.length;x++)
					{
						for(int y=0;y<minefield[0].length;y++)
						{
							final int x1=x;
							final int y1=y;
							minefield[x1][y1]=new Tile(new JToggleButton(),0,false,false);
							if(dT)
							{
								minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("closedTile.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
							}
							else if(t1)
							{
								minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("close.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
							}
							else
							{
								minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_00.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
							}
							minefield[x][y].getButton().addMouseListener(new MouseAdapter()
							{
								public void mouseReleased(MouseEvent e)
								{
									if(e.getModifiers() == MouseEvent.BUTTON3_MASK&&!win)
									{
										if(!minefield[x1][y1].getFlagged()&&!minefield[x1][y1].getClicked())
										{
											flag(x1,y1,minefield);
											minefield[x1][y1].setFlagged(true);
											mineCount--;
										}
										else if(!minefield[x1][y1].getClicked())
										{
											if(dT)
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("closedTile.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											else if(t1)
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("close.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											else
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_00.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											minefield[x1][y1].setFlagged(false);
											mineCount++;
										}
									}
									if(e.getModifiers() == 16&&!win)
									{
										if(!firstClick&&!minefield[x1][y1].getFlagged())
										{
											runTimer();
											firstClick=true;
											minefield[x1][y1].setState(0);
											minefield[x1][y1].setClicked(true);
											if(dT)
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("empty.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											else if(t1)
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("default.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											else
											{
												minefield[x1][y1].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_01.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
											}
											fillGrid(x1,y1,minefield,mineCount);
											reveal(x1,y1,minefield);
										}
										else if(!minefield[x1][y1].getFlagged())
										{
											minefield[x1][y1].setClicked(true);
											spaceSetter(x1,y1,minefield[x1][y1].getState(),minefield);
											if(minefield[x1][y1].getState()==0)
											{
												reveal(x1,y1,minefield);
											}
											if(minefield[x1][y1].getState()==-1)
											{
												endGame();
											}
											win=true;
											for(int x=0;x<minefield.length;x++)
											{
												for(int y=0;y<minefield[0].length;y++)
												{
													if(minefield[x][y].getState()!=-1&&minefield[x][y].getClicked()!=true)
														win=false;
												}
											}
											if(gameEnd)
												win=false;
											if(win)
												JOptionPane.showMessageDialog(frame,"You won the game! Start a new game!","Minesweeper",JOptionPane.INFORMATION_MESSAGE);
										}
									}
								}
							});
							gamePanel.add(minefield[x][y].getButton());
						}
					}
					frame.add(gamePanel,BorderLayout.CENTER);
					frame.revalidate();
				}
		    }
		});
		icon=new JMenu("Icon");
		original=new JMenuItem(new AbstractAction("Default Minesweeper theme") {
		    public void actionPerformed(ActionEvent e) {
		        dT=true;
		        t1=false;
		        t2=false;
		        for(int x=0;x<minefield.length;x++)
		        {
					for(int y=0;y<minefield[0].length;y++)
					{
						if(minefield[x][y].getClicked())
							spaceSetter(x,y,minefield[x][y].getState(),minefield);
						else minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("closedTile.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
					}
				}
		    }
		});
		theme1=new JMenuItem(new AbstractAction("Theme 1") {
		    public void actionPerformed(ActionEvent e) {
				dT=false;
		        t1=true;
		        t2=false;
		        for(int x=0;x<minefield.length;x++)
		        {
					for(int y=0;y<minefield[0].length;y++)
					{
						if(minefield[x][y].getClicked())
							spaceSetter(x,y,minefield[x][y].getState(),minefield);
						else minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("close.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
					}
				}
		        // Button pressed logic goes here
		    }
		});
		theme2=new JMenuItem(new AbstractAction("Theme 2") {
		    public void actionPerformed(ActionEvent e) {
				dT=false;
		        t1=false;
		        t2=true;
		        for(int x=0;x<minefield.length;x++)
		        {
					for(int y=0;y<minefield[0].length;y++)
					{
						if(minefield[x][y].getClicked())
							spaceSetter(x,y,minefield[x][y].getState(),minefield);
						else minefield[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_00.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
					}
				}
		        // Button pressed logic goes here
		    }
		});

		controls=new JMenu("Controls");
		controls.add(new JMenuItem("- Left-click an empty square to reveal it"));
		controls.add(new JMenuItem("- Right-click an empty square to flag it"));
		controls.add(new JMenuItem("- The number on each tile represetns how many mines are in a 1 tile radius"));
		controls.add(new JMenuItem("- Don't hit a mine!"));

		game.add(beginner);
		game.add(intermediate);
		game.add(expert);
		icon.add(original);
		icon.add(theme1);
		icon.add(theme2);
		menuBar.add(game);
		menuBar.add(icon);
		menuBar.add(controls);

		mineCounter=new JLabel("                Mine count: "+mineCount+"                ");
		mineTimer=new JLabel("                Time: "+t+"                ");
		resetButton.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("smile.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
		resetButton.setPreferredSize(new Dimension(50,50));
		buttonPanel.add(resetButton,BorderLayout.CENTER);
		scorePanel.add(mineCounter,BorderLayout.WEST);
		scorePanel.add(mineTimer,BorderLayout.EAST);
		scorePanel.add(buttonPanel,BorderLayout.CENTER);
		topPanel.add(menuBar,BorderLayout.NORTH);
		topPanel.add(scorePanel,BorderLayout.NORTH);

		frame.add(gamePanel,BorderLayout.CENTER);
		frame.add(topPanel,BorderLayout.NORTH);
		frame.setSize(1200,800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void runTimer()
	{
		time=0;
		t="000";
		mineTimer.setText("                Time: "+t+"                ");
		mineCounter.setText("                Mine count: "+mineCount+"                ");
		timer.cancel();
		timer=new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(firstClick&&!gameEnd&&!win)
				{
					time++;
					t="";
					for(int x=0;x<3-(""+time).length();x++)
					{
						t+="0";
					}
					t+=time;
					mineTimer.setText("                Time: "+t+"                ");
					mineCounter.setText("                Mine count: "+mineCount+"                ");
				}
			}
		}, 1000,1000);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		mineCounter.setText("                Mine count: "+mineCount+"                ");
		mineTimer.setText("                Time: "+t+"                ");
	}
	public void resetMC(int mc)
	{
		mineCount=mc;
		mineCounter.setText("                Mine count: "+mc+"                ");
	}
	public void fillGrid(int startX,int startY,Tile[][] mines, int mineCount)
	{
		for(int x=0;x<mineCount;x++)
		{
			int row=(int)(Math.random()*mines.length);
			int col=(int)(Math.random()*mines[0].length);
			if(mines[row][col].getState()!=-1&&(!(row==startX&&col==startY)&&row+1!=startX&&row-1!=startX&&col+1!=startY&&col-1!=startY)) //first thing is that if its not a mine
			{
				mines[row][col].setState(-1); //-1 is the state for a mine
			}
			else
			{
				x--;
			}
		}
		for(int x=0;x<mines.length;x++)
		{
			for(int y=0;y<mines[0].length;y++)
			{
				int mc=0;
				if(mines[x][y].getState()!=-1&&!(x==startX&&y==startY))
				{
					if(y+1<mines[0].length&&mines[x][y+1].getState()==-1)
					{
						mc++;
					}
					if(y+1<mines[0].length&&x+1<mines.length&&mines[x+1][y+1].getState()==-1)
					{
						mc++;
					}
					if(x+1<mines.length&&mines[x+1][y].getState()==-1)
					{
						mc++;
					}
					if(y-1>-1&&mines[x][y-1].getState()==-1)
					{
						mc++;
					}
					if(y-1>-1&&x-1>-1&&mines[x-1][y-1].getState()==-1)
					{
						mc++;
					}
					if(x-1>-1&&mines[x-1][y].getState()==-1)
					{
						mc++;
					}
					if(x-1>-1&&y+1<mines[0].length&&mines[x-1][y+1].getState()==-1)
					{
						mc++;
					}
					if(x+1<mines.length&&y-1>-1&&mines[x+1][y-1].getState()==-1)
					{
						mc++;
					}
				mines[x][y].setState(mc);
				//spaceSetter(x,y,mc,mines);
				}
			}
		}
	}
	public void flag(int x,int y,Tile[][] mines)
	{	if(dT)
		{
			mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("flagged.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
		}
		else if(t1)
		{
			mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("flag.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
		}
		else
		{
			mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_02.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
		}
	}
	public void endGame()
	{
		for(int x=0;x<minefield.length;x++)
		{
			for(int y=0;y<minefield[0].length;y++)
			{
				if(minefield[x][y].getState()==-1)
					spaceSetter(x,y,minefield[x][y].getState(),minefield);
			}
			gameEnd=true;
		}
	}
	public void spaceSetter(int x,int y,int num,Tile[][] mines)
	{
		if(dT)
		{
			switch(num)
			{
				case -1:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("mine.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 0:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("empty.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 1:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("one.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 2:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("two.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 3:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("three.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 4:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("four.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 5:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("five.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 6:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("six.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 7:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("seven.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 8:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("eight.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
			}
		}
		else if(t1)
		{
			switch(num)
			{
				case -1:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("mine1.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 0:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("default.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 1:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("1.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 2:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("2.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 3:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("3.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 4:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("4.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 5:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("5.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 6:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("6.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 7:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("7.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 8:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("8.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
			}
		}
		else
		{
			switch(num)
			{
				case -1:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_05.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 0:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_04.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 1:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_08.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 2:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_09.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 3:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_10.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 4:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_11.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 5:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_12.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 6:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_13.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 7:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_14.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
				case 8:
					mines[x][y].getButton().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("minesweeper_15.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)));
				break;
			}
		}
	}
	public void adjustmentValueChanged(AdjustmentEvent e)
	{
		repaint();
	}
	public void reveal(int x, int y,Tile[][] mines)
	{
		if(y+1<mines[0].length&&mines[x][y+1].getState()!=-1&&mines[x][y+1].getClicked()==false)
		{
			mines[x][y+1].setClicked(true);
			spaceSetter(x,y+1,mines[x][y+1].getState(),mines);
			if(mines[x][y+1].getState()==0)
				reveal(x,y+1,mines);
		}
		if(y+1<mines[0].length&&x+1<mines.length&&mines[x+1][y+1].getState()!=-1&&mines[x+1][y+1].getClicked()==false)
		{
			mines[x+1][y+1].setClicked(true);
			spaceSetter(x+1,y+1,mines[x+1][y+1].getState(),mines);
			if(mines[x+1][y+1].getState()==0)
			reveal(x+1,y+1,mines);
		}
		if(x+1<mines.length&&mines[x+1][y].getState()!=-1&&mines[x+1][y].getClicked()==false)
		{
			mines[x+1][y].setClicked(true);
			spaceSetter(x+1,y,mines[x+1][y].getState(),mines);
			if(mines[x+1][y].getState()==0)
			reveal(x+1,y,mines);
		}
		if(y-1>-1&&mines[x][y-1].getState()!=-1&&mines[x][y-1].getClicked()==false)
		{
			mines[x][y-1].setClicked(true);
			spaceSetter(x,y-1,mines[x][y-1].getState(),mines);
			if(mines[x][y-1].getState()==0)
			reveal(x,y-1,mines);
		}
		if(y-1>-1&&x-1>-1&&mines[x-1][y-1].getState()!=-1&&mines[x-1][y-1].getClicked()==false)
		{
			mines[x-1][y-1].setClicked(true);
			spaceSetter(x-1,y-1,mines[x-1][y-1].getState(),mines);
			if(mines[x-1][y-1].getState()==0)
			reveal(x-1,y-1,mines);
		}
		if(x-1>-1&&mines[x-1][y].getState()!=-1&&mines[x-1][y].getClicked()==false)
		{
			mines[x-1][y].setClicked(true);
			spaceSetter(x-1,y,mines[x-1][y].getState(),mines);
			if(mines[x-1][y].getState()==0)
			reveal(x-1,y,mines);
		}
		if(x-1>-1&&y+1<mines[0].length&&mines[x-1][y+1].getState()!=-1&&mines[x-1][y+1].getClicked()==false)
		{
			mines[x-1][y+1].setClicked(true);
			spaceSetter(x-1,y+1,mines[x-1][y+1].getState(),mines);
			if(mines[x-1][y+1].getState()==0)
			reveal(x-1,y+1,mines);
		}
		if(x+1<mines.length&&y-1>-1&&mines[x+1][y-1].getState()!=-1&&mines[x+1][y-1].getClicked()==false)
		{
			mines[x+1][y-1].setClicked(true);
			spaceSetter(x+1,y-1,mines[x+1][y-1].getState(),mines);
			if(mines[x+1][y-1].getState()==0)
			reveal(x+1,y-1,mines);
		}
	}
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}
	public static void main(String[] args)
	{
		Minesweeper app=new Minesweeper();
	}
	public class Tile
	{
		JToggleButton button;
		int state;
		boolean flagged;
		boolean clicked;
		public Tile(JToggleButton button, int state,boolean flagged,boolean clicked)
		{
			this.button=button;
			this.state=state;
			this.flagged=flagged;
			this.clicked=clicked;
		}
		public int getState()
		{
			return state;
		}
		public void setState(int state)
		{
			this.state=state;
		}
		public boolean getFlagged()
		{
			return flagged;
		}
		public void setFlagged(boolean flagged)
		{
			this.flagged=flagged;
		}
		public boolean getClicked()
		{
			return clicked;
		}
		public void setClicked(boolean clicked)
		{
			this.clicked=clicked;
		}
		public JToggleButton getButton()
		{
			return button;
		}
		public void setButton(JToggleButton button)
		{
			this.button=button;
		}
	}
}