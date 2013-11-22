package source;


import java.util.Random;


public class TicTacToe  implements Game
{
	private boolean a1;
	private boolean a2;
	private boolean a3;
	private boolean a4;
	private boolean a5;
	private boolean a6;
	private boolean a7;
	private boolean a8;
	private boolean a9;
	private boolean b1;
	private boolean b2;
	private boolean b3;
	private boolean b4;
	private boolean b5;
	private boolean b6;
	private boolean b7;
	private boolean b8;
	private boolean b9;
	private boolean player1;
	private int [] places={0,0,0,0,0,0,0,0,0};
	public MerlinCore1 mc1;
	public TicTacToe(MerlinCore1 core)
	{
		mc1=core;
	}
	
	public boolean player1Turn()
	{
		return player1;
	}
	
	public boolean spotTaken(int spot)
	{
		if(spot==1&&a1)return true;
		if(spot==2&&a2)return true;
		if(spot==3&&a3)return true;
		if(spot==4&&a4)return true;
		if(spot==5&&a5)return true;
		if(spot==6&&a6)return true;
		if(spot==7&&a7)return true;
		if(spot==8&&a8)return true;
		if(spot==9&&a9)return true;
		if(spot==1&&b1)return true;
		if(spot==2&&b2)return true;
		if(spot==3&&b3)return true;
		if(spot==4&&b4)return true;
		if(spot==5&&b5)return true;
		if(spot==6&&b6)return true;
		if(spot==7&&b7)return true;
		if(spot==8&&b8)return true;
		if(spot==9&&b9)return true;
		return false;
	}
	
	public void switchPLayer()
	{
		if(player1)player1=false;
		else player1=true;
	}
	
	public void getNextMove(int spot)	
	{		
		if(spotTaken(spot))
		{	
			
		}
		else
		{
			if (player1) 
			{
				if (spot == 1){a1 = true;mc1.buttonOn(spot);places[0]=1;}
				if (spot == 2){a2 = true;mc1.buttonOn(spot);places[1]=1;}
				if (spot == 3){a3 = true;mc1.buttonOn(spot);places[2]=1;}
				if (spot == 4){a4 = true;mc1.buttonOn(spot);places[3]=1;}
				if (spot == 5){a5 = true;mc1.buttonOn(spot);places[4]=1;}
				if (spot == 6){a6 = true;mc1.buttonOn(spot);places[5]=1;}
				if (spot == 7){a7 = true;mc1.buttonOn(spot);places[6]=1;}
				if (spot == 8){a8 = true;mc1.buttonOn(spot);places[7]=1;}
				if (spot == 9){a9 = true;mc1.buttonOn(spot);places[8]=1;}
			}
			else 
			{
				if (spot == 1){b1 = true;mc1.buttonFlash(spot);places[0]=2;}
				if (spot == 2){b2 = true;mc1.buttonFlash(spot);places[1]=2;}
				if (spot == 3){b3 = true;mc1.buttonFlash(spot);places[2]=2;}
				if (spot == 4){b4 = true;mc1.buttonFlash(spot);places[3]=2;}
				if (spot == 5){b5 = true;mc1.buttonFlash(spot);places[4]=2;}
				if (spot == 6){b6 = true;mc1.buttonFlash(spot);places[5]=2;}
				if (spot == 7){b7 = true;mc1.buttonFlash(spot);places[6]=2;}
				if (spot == 8){b8 = true;mc1.buttonFlash(spot);places[7]=2;}
				if (spot == 9){b9 = true;mc1.buttonFlash(spot);places[8]=2;}
			}
			
			switchPLayer();
		}
	}
	public boolean game1()
	{
		if(a1&&a2&&a3)return true;if(a4&&a1&&a7)return true;if(a7&&a5&&a3)return true;
		if(a4&&a5&&a6)return true;if(a8&&a5&&a2)return true;if(a9&&a5&&a1)return true;
		if(a7&&a8&&a9)return true;if(a3&&a9&&a6)return true;
		
		return false;
	}
	
	public boolean game2()
	{
		if(b1&&b2&&b3)return true;if(b4&&b1&&b7)return true;if(b7&&b5&&b3)return true;
		if(b4&&b5&&b6)return true;if(b8&&b5&&b2)return true;if(b9&&b5&&b1)return true;
		if(b7&&b8&&b9)return true;if(b3&&b9&&b6)return true;
		
		return false;
	}
	
	public boolean tie()
	{
		if((a1||b1)&&(a2||b2)&&(a3||b3)&&(a4||b4)&&(a5||b5)&&(a6||b6)
		&&(a7||b7)&&(a8||b8)&&(a9||b9)&&!game1()&&!game2())return true;
		
		return false;		
	}
	public boolean gameOver()
	{
		
		if(game1()||game2()||tie())return true;		
		return false;
	}	

	public int chooseRandomSpot()
	{
		Random randy=new Random();
		int spot=randy.nextInt(9)+1;
		if(spotTaken(spot))
		do spot=randy.nextInt(9)+1;
		while(spotTaken(spot));
		return spot;
	}
	public int chooseRandomSpot(int[] spots)
	{		
		Random randy=new Random();
		int spot=0;		
		do
		{
			spot=randy.nextInt(spots.length);			
			
		}while(spotTaken(spots[spot]));
		
		
		return spots[spot];
	}
	public boolean allTaken(int[]spots)
	{
		int taken=0;
		for(int check=0;check<spots.length;check++)
		{
			if(spotTaken(spots[check])){taken++;}
		}
		if(taken==spots.length)return true;
		return false;
	}
	public int chooseSpot()
	{
		int selection = 0;
		int [] bestSpots={1,3,5,7,9};
		if(canWin()){selection=win();}
		else
			if(oppCanWin()){selection=blockOtherPlayer();}
			else 
				if(!spotTaken(5))selection=5;
				else 
					if(!allTaken(bestSpots)){selection=chooseRandomSpot(bestSpots);}
					else {chooseRandomSpot();}


		if(selection==0)selection=chooseRandomSpot();		
		return selection;
	}
	
	public boolean oppCanWin()
	{	
		int[][] places=placesTaken();
		if(player1)
		{
			for(int group=0;group<8;group++)
			{
				int oppCount=0;int spaces=0;
				for(int space=0;space<3;space++)
				{
					if(places[group][space]==2)oppCount++;
					if(places[group][space]==0&&places[group][space]!=1)spaces++;
					if(oppCount==2&&spaces==1){return true;}
				}
								
			}
		}

		if(!player1)
		{
			for(int group=0;group<8;group++)
			{
				int oppCount=0;int spaces=0;

				for(int space=0;space<3;space++)
				{
					if(places[group][space]==1)oppCount++;
					if(places[group][space]==0&&places[group][space]!=2)spaces++;
					if(oppCount==2&&spaces==1){return true;}
				}								
			}
		}
		return false;
	}
	public boolean canWin()
	{	
		int[][] places=placesTaken();
		if(!player1)
		{
			for(int group=0;group<8;group++)
			{
				int count=0;int spaces=0;
				for(int space=0;space<3;space++)
				{
					if(places[group][space]==2)count++;
					if(places[group][space]==0)spaces++;
					if(count==2&&spaces==1){return true;}
				}
								
			}
		}

		if(player1)
		{
			for(int group=0;group<8;group++)
			{
				int count=0;int spaces=0;

				for(int space=0;space<3;space++)
				{
					if(places[group][space]==1)count++;
					if(places[group][space]==0)spaces++;
					if(count==2&&spaces==1){return true;}
				}								
			}
		}
		return false;
	}
	
	public int win()
	{
		int[][] places=placesTaken();
		int win=0;
		if(!player1)
		{
			for(int group=0;group<8;group++)
			{
				int count=0;int spaces=0;				
				int freeSpace=3;
				for(int space=0;space<3;space++)
				{
					if(places[group][space]==2){count++;}
					if(places[group][space]==0){freeSpace=space;spaces++;}					
				}	
				if(count==2&&spaces==1)
					if(freeSpace!=3){win=getNumber(group,freeSpace);}
					else {win=getNumber(group,2);}								
								
			}
		}
		
		if(player1)
		{
			for(int group=0;group<8;group++)
			{
				int count=0;int spaces=0;				
				int freeSpace=3;
				for(int space=0;space<3;space++)
				{
					if(places[group][space]==1){count++;}
					if(places[group][space]==0){freeSpace=space;spaces++;}					
				}	
				if(count==2&&spaces==1)
					if(freeSpace!=3)win=getNumber(group,freeSpace);
					else win=getNumber(group,2);								
									
			}
		}		
		return win;
	}
	public int blockOtherPlayer()
	{
		int[][] places=placesTaken();
		int block=0;
		if(player1)
		{
			for(int group=0;group<8;group++)
			{
				int oppCount=0;				
				int freeSpace=3;
				int spaces=0;
				for(int space=0;space<3;space++)
				{
					if(places[group][space]==2){oppCount++;}
					if(places[group][space]==0){freeSpace=space;spaces++;}					
				}	
				if(oppCount==2&&spaces==1)
				{
					if(freeSpace!=3){block=getNumber(group,freeSpace);}
					else {block=getNumber(group,2);}
					break;
				}								
								
			}
		}
		
		if(!player1)
		{
			for(int group=0;group<8;group++)
			{
				int oppCount=0;				
				int freeSpace=3;
				int spaces=0;
				for(int space=0;space<3;space++)
				{
					if(places[group][space]==1){oppCount++;}
					if(places[group][space]==0){freeSpace=space;spaces++;}					
				}	
				if(oppCount==2&&spaces==1)
				{
					if(freeSpace!=3){block=getNumber(group,freeSpace);}
					else {block=getNumber(group,2);}
					break;
				}					
			}
		}
				
		
		return block;
	}
	int[][] placesTaken()
	{
		int[][] spots =new int [8][3];
		boolean[]status1=gridStatus1();
		boolean[]status2=gridStatus2();
		for(int checks=0;checks<9;checks++)
		{
			int[] places=calcSpotInGrid(checks+1);
			if(status1[checks])
			{				
				for(int i=0;i<places.length;i+=2)
				{
					spots[places[i]][places[i+1]]=1;
				}
			}
			if(status2[checks])
			{				
				for(int i=0;i<places.length;i+=2)
				{
					spots[places[i]][places[i+1]]=2;
				}
			}
			if(!status1[checks]&&!status2[checks])
			{				
				for(int i=0;i<places.length;i+=2)
				{
					spots[places[i]][places[i+1]]=0;
				}
			}
		}
		
		return spots;
		
	}
	int[] calcSpotInGrid(int place)
	{
		int[]places = null;
		if(place==1)places=new int[]{0,0,3,0,6,0};
		if(place==2)places=new int[]{0,1,4,0};
		if(place==3)places=new int[]{0,2,5,0,7,0};
		if(place==4)places=new int[]{1,0,3,1};
		if(place==5)places=new int[]{1,1,4,1,6,1,7,1};
		if(place==6)places=new int[]{1,2,5,1};
		if(place==7)places=new int[]{2,0,3,2,7,2};
		if(place==8)places=new int[]{2,1,4,2};
		if(place==9)places=new int[]{2,2,5,2,6,2};
		
		return places;
	}
	public boolean[] gridStatus1()
	{
		boolean[] status={a1,a2,a3,a4,a5,a6,a7,a8,a9};
		return status;
	}
	
	public boolean[] gridStatus2()
	{
		boolean[] status={b1,b2,b3,b4,b5,b6,b7,b8,b9};
		return status;
	}
	
	public int getNumber(int group, int place)
	{
		int num=0;		
		if(group>=0&&group<=2)
			for(int i=0;i<=group;i++)
				{
					for(int p=0;p<3;p++)
					{
						num++;System.out.println(num);		
						if(i==group&&p==place)break;
						
					}
					
				}
		if(group>=3&&group<=5)
		{
			num=1;
			for(int g=0;g<=group-3;g++)
			{	
				num=1+g;
				for(int p=0;p<3;p++)									
					{if(g==group-3&&p==place)break;
					num+=3;}												
			}				
		}
		if(group==6&&place==0)num=1;
		if((group==6&&place==1)||(group==7&&place==1))num=5;		
		if(group==6&&place==2)num=9;
		if(group==7&&place==0)num=3;
		if(group==7&&place==2)num=7;
			
		
		return num;
	}	

	@Override
	public void startGame() 
	{
		a1=false;a2=false;a3=false;a4=false;a5=false;a6=false;a7=false;a8=false;a9=false;
		b1=false;b2=false;b3=false;b4=false;b5=false;b6=false;b7=false;b8=false;b9=false;
		player1=true;
	}

	@Override
	public void clickProcessor(int num) 
	{
		if(!gameOver()) getNextMove(num);
		if(game1())mc1.makeToast("Player 1 Wins!");
		if(game2())mc1.makeToast("Player 2 Wins!");
		if(tie())mc1.makeToast("The game is tied!");


	}

	@Override
	public void controlProcessor(int num) 
	{
		if(!gameOver())if(num==mc1.COMP_TURN)getNextMove(chooseSpot());
		
	}

}

