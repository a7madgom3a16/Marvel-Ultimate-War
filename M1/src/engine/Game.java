package engine;

import java.awt.*;
import java.io.*;
import java.util.*;
import exceptions.*;
import model.abilities.*;
import model.effects.*;
import model.world.*;
import java.lang.Math;

public class Game {
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private Player firstPlayer;
	private Player secondPlayer;
	private Object[][] board;
	private PriorityQueue turnOrder;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private final static int BOARDWIDTH = 5;
	private final static int BOARDHEIGHT = 5;

	public Game(Player first,Player second) {
		firstPlayer = first;
		secondPlayer = second;
		availableChampions = new ArrayList<Champion>();
		availableAbilities = new ArrayList<Ability>();
		board = new Object[BOARDWIDTH][BOARDHEIGHT];
		turnOrder = new PriorityQueue(6);
		placeChampions();
		placeCovers();
		prepareChampionTurns();
	}

	public static void loadAbilities(String filePath) throws IOException {
		availableAbilities = new ArrayList<Ability>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Ability a = null;
			AreaOfEffect ar = null;
			switch (content[5]) {
			case "SINGLETARGET":
				ar = AreaOfEffect.SINGLETARGET;
				break;
			case "TEAMTARGET":
				ar = AreaOfEffect.TEAMTARGET;
				break;
			case "SURROUND":
				ar = AreaOfEffect.SURROUND;
				break;
			case "DIRECTIONAL":
				ar = AreaOfEffect.DIRECTIONAL;
				break;
			case "SELFTARGET":
				ar = AreaOfEffect.SELFTARGET;
				break;

			}
			Effect e = null;
			if (content[0].equals("CC")) {
				switch (content[7]) {
				case "Disarm":
					e = new Disarm(Integer.parseInt(content[8]));
					break;
				case "Dodge":
					e = new Dodge(Integer.parseInt(content[8]));
					break;
				case "Embrace":
					e = new Embrace(Integer.parseInt(content[8]));
					break;
				case "PowerUp":
					e = new PowerUp(Integer.parseInt(content[8]));
					break;
				case "Root":
					e = new Root(Integer.parseInt(content[8]));
					break;
				case "Shield":
					e = new Shield(Integer.parseInt(content[8]));
					break;
				case "Shock":
					e = new Shock(Integer.parseInt(content[8]));
					break;
				case "Silence":
					e = new Silence(Integer.parseInt(content[8]));
					break;
				case "SpeedUp":
					e = new SpeedUp(Integer.parseInt(content[8]));
					break;
				case "Stun":
					e = new Stun(Integer.parseInt(content[8]));
					break;
				}
			}
			switch (content[0]) {
			case "CC":
				a = new CrowdControlAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), e);
				break;
			case "DMG":
				a = new DamagingAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), Integer.parseInt(content[7]));
				break;
			case "HEL":
				a = new HealingAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), Integer.parseInt(content[7]));
				break;
			}
			availableAbilities.add(a);
			line = br.readLine();
		}
		br.close();
	}

	public static void loadChampions(String filePath) throws IOException {
		availableChampions = new ArrayList<Champion>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Champion c = null;
			switch (content[0]) {
			case "A":
				c = new AntiHero(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;

			case "H":
				c = new Hero(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			case "V":
				c = new Villain(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			}

			c.getAbilities().add(findAbilityByName(content[8]));
			c.getAbilities().add(findAbilityByName(content[9]));
			c.getAbilities().add(findAbilityByName(content[10]));
			availableChampions.add(c);
			line = br.readLine();
		}
		br.close();
	}

	public static Ability findAbilityByName(String name) {
		for (Ability a : availableAbilities) {
			if (a.getName().equals(name))
				return a;
		}
		return null;
	}
	
	public static Champion findChampionByName(String name) {
		for (Champion c : availableChampions) {
			if (c.getName().equals(name))
				return c;
		}
		return null;
	}

	public void placeCovers() {
		int i = 0;
		while (i < 5) {
			int x = ((int) (Math.random() * (BOARDWIDTH - 2))) + 1;
			int y = (int) (Math.random() * BOARDHEIGHT);

			if (board[x][y] == null) {
				board[x][y] = new Cover(x, y);
				i++;
			}
		}

	}

	public void placeChampions() {
		int i = 1;
		for (Champion c : firstPlayer.getTeam()) {
			board[4][i] = c;
			c.setLocation(new Point(4,i));
			i++;
		}
		i = 1;
		for (Champion c : secondPlayer.getTeam()) {
			board[0][i] = c;
			c.setLocation(new Point(0,i));
			i++;
		}
	
	}

	public static ArrayList<Champion> getAvailableChampions() {
		return availableChampions;
	}

	public static ArrayList<Ability> getAvailableAbilities() {
		return availableAbilities;
	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public Player getSecondPlayer() {
		return secondPlayer;
	}

	public Object[][] getBoard() {
		return board;
	}

	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}

	public boolean isFirstLeaderAbilityUsed() {
		return firstLeaderAbilityUsed;
	}

	public boolean isSecondLeaderAbilityUsed() {
		return secondLeaderAbilityUsed;
	}

	public static int getBoardwidth() {
		return BOARDWIDTH;
	}

	public static int getBoardheight() {
		return BOARDHEIGHT;
	}
	
	public Champion getCurrentChampion() {
		return (Champion)turnOrder.peekMin();
	}
	
	public Player checkGameOver() {
		ArrayList<Champion> t1 = firstPlayer.getTeam();
		ArrayList<Champion> t2 = secondPlayer.getTeam();
		if (t1.size() == 0)
				return secondPlayer;
		if (t2.size() == 0)
				return firstPlayer;
		return null;
	}
	
	public void move(Direction d) throws UnallowedMovementException,NotEnoughResourcesException {
		if(this.getCurrentChampion().getCurrentActionPoints()<1)
			throw new NotEnoughResourcesException("Not enough action points");
		if(this.getCurrentChampion().getCondition().equals(Condition.ROOTED))
			throw new UnallowedMovementException("Cannot move, champions is rooted");
		if(d.equals(Direction.UP)) {
			if(this.getCurrentChampion().getLocation().getX()==0)
				throw new UnallowedMovementException("Cannot move this way");
			else if(board[(int)this.getCurrentChampion().getLocation().getX()-1][(int)this.getCurrentChampion().getLocation().getY()] != null)
				throw new UnallowedMovementException("Cannot move, cell is not empty");
			else {
				board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y] = null;
				board[this.getCurrentChampion().getLocation().x-1][this.getCurrentChampion().getLocation().y] = getCurrentChampion();
				this.getCurrentChampion().setLocation(new Point((int)this.getCurrentChampion().getLocation().getX()-1,(int)this.getCurrentChampion().getLocation().getY()));
				this.getCurrentChampion().setCurrentActionPoints(this.getCurrentChampion().getCurrentActionPoints()-1);
			}
		}	
		if(d.equals(Direction.DOWN)) {
			if(this.getCurrentChampion().getLocation().getX()==4)
				throw new UnallowedMovementException("Cannot move this way");
			else if(board[(int)this.getCurrentChampion().getLocation().getX()+1][(int)this.getCurrentChampion().getLocation().getY()] != null)
				throw new UnallowedMovementException("Cannot move, cell is not empty");
			else {
				board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y] = null;
				board[this.getCurrentChampion().getLocation().x+1][this.getCurrentChampion().getLocation().y] = getCurrentChampion();
				this.getCurrentChampion().setLocation(new Point((int)this.getCurrentChampion().getLocation().getX()+1,(int)this.getCurrentChampion().getLocation().getY()));
				this.getCurrentChampion().setCurrentActionPoints(this.getCurrentChampion().getCurrentActionPoints()-1);
			}
		}
		if(d.equals(Direction.LEFT)) {
			if(this.getCurrentChampion().getLocation().getY()==0)
				throw new UnallowedMovementException("Cannot move this way");
			else if(board[(int)this.getCurrentChampion().getLocation().getX()][(int)this.getCurrentChampion().getLocation().getY()-1] != null)
				throw new UnallowedMovementException("Cannot move, cell is not empty");
			else {
				board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y] = null;
				board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y - 1] = getCurrentChampion();
				this.getCurrentChampion().setLocation(new Point((int)this.getCurrentChampion().getLocation().getX(),(int)this.getCurrentChampion().getLocation().getY()-1));
				this.getCurrentChampion().setCurrentActionPoints(this.getCurrentChampion().getCurrentActionPoints()-1);
			}
		}
		if(d.equals(Direction.RIGHT)) {
			if(this.getCurrentChampion().getLocation().getY()==4)
				throw new UnallowedMovementException("Cannot move this way");
			else if(board[(int)this.getCurrentChampion().getLocation().getX()][(int)this.getCurrentChampion().getLocation().getY()+1] != null)
				throw new UnallowedMovementException("Cannot move, cell is not empty");
			else {
				board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y] = null;
				board[this.getCurrentChampion().getLocation().x][this.getCurrentChampion().getLocation().y + 1] = getCurrentChampion();
				this.getCurrentChampion().setLocation(new Point((int)this.getCurrentChampion().getLocation().getX(),(int)this.getCurrentChampion().getLocation().getY()+1));
				this.getCurrentChampion().setCurrentActionPoints(this.getCurrentChampion().getCurrentActionPoints()-1);
			}
		}
	}
	
	public void attack(Direction d) throws InvalidTargetException,NotEnoughResourcesException,ChampionDisarmedException {
		if(this.getCurrentChampion().getCurrentActionPoints()<2)
			throw new NotEnoughResourcesException("Not enough action points");
		for(int i=0;i<this.getCurrentChampion().getAppliedEffects().size();i++)
			if(this.getCurrentChampion().getAppliedEffects().get(i) instanceof Disarm)
				throw new ChampionDisarmedException("Cannot attack, champion disarmed");
		this.getCurrentChampion().setCurrentActionPoints(this.getCurrentChampion().getCurrentActionPoints()-2);
		int r = this.getCurrentChampion().getAttackRange();
		Point p = this.getCurrentChampion().getLocation();
		Point t = null;
		Object a = null;
		if(d.equals(Direction.UP)) {
			if(p.x==0)
				return;
			for(int i=1;i<=r;i++) {
				if(p.x-i<0)
					return;
				else {
					if(board[p.x-i][p.y]==null || (board[p.x-i][p.y] instanceof Champion &&
						((firstPlayer.getTeam().contains((Champion)board[p.x-i][p.y]) &&
						firstPlayer.getTeam().contains(this.getCurrentChampion())) ||
						(secondPlayer.getTeam().contains((Champion)board[p.x-i][p.y])) &&
						secondPlayer.getTeam().contains(this.getCurrentChampion()))))
						continue;
					t = new Point((p.x-i),(p.y));
					a = board[t.x][t.y];
					break;
				}
			}
		}
		else if(d.equals(Direction.DOWN)) {
			if(p.x==4)
				return;
			for(int i=1;i<=r;i++) {
				if(p.x+i>4)
					return;
				else {
					if(board[p.x+i][p.y]==null || (board[p.x+i][p.y] instanceof Champion &&
						((firstPlayer.getTeam().contains((Champion)board[p.x+i][p.y]) &&
						firstPlayer.getTeam().contains(this.getCurrentChampion())) ||
						(secondPlayer.getTeam().contains((Champion)board[p.x+i][p.y]) &&
						secondPlayer.getTeam().contains(this.getCurrentChampion())))))
						continue;
					t = new Point((p.x+i),(p.y));
					a = board[t.x][t.y];
					break;
				}
			}
		}
		else if(d.equals(Direction.LEFT)) {
			if(p.y==0)
				return;
			for(int i=1;i<=r;i++) {
				if(p.y-i<0)
					return;
				else {
					if(board[p.x][p.y-i]==null || (board[p.x][p.y-i] instanceof Champion &&
						((firstPlayer.getTeam().contains((Champion)board[p.x][p.y-i]) &&
						firstPlayer.getTeam().contains(this.getCurrentChampion())) ||
						(secondPlayer.getTeam().contains((Champion)board[p.x][p.y-i]) &&
						secondPlayer.getTeam().contains(this.getCurrentChampion())))))
						continue;
					t = new Point((p.x),(p.y-i));
					a = board[t.x][t.y];
					break;
				}
			}
		}
		else {
			if(p.y==0)
				return;
			for(int i=1;i<=r;i++) {
				if(p.y+i>4)
					return;
				else {
					if(board[p.x][p.y+i]==null || (board[p.x][p.y+i] instanceof Champion &&
						((firstPlayer.getTeam().contains((Champion)board[p.x][p.y+i]) &&
						firstPlayer.getTeam().contains(this.getCurrentChampion())) ||
						(secondPlayer.getTeam().contains((Champion)board[p.x][p.y+i]) &&
						secondPlayer.getTeam().contains(this.getCurrentChampion())))))
						continue;
					t = new Point((p.x),(p.y+i));
					a = board[t.x][t.y];
					break;
				}
			}
		}
		attackHelper(a,t);
	}
	
	public void attackHelper(Object a,Point t) {
		if(a instanceof Cover) {
			((Cover)a).setCurrentHP(((Cover)a).getCurrentHP()-this.getCurrentChampion().getAttackDamage());
			if(((Cover)a).getCurrentHP()<=0)
				board[t.x][t.y] = null;
			return;
		}
		else if(a instanceof Champion) {
			double c = Math.random();
			for(int j=0;j<((Champion)a).getAppliedEffects().size();j++)
				if(((Champion)a).getAppliedEffects().get(j) instanceof Shield) {
					Effect e = ((Champion)a).getAppliedEffects().get(j);
					((Champion)a).getAppliedEffects().remove(e);
					e.remove((Champion) a);
					return;
				}
			for(int j=0;j<((Champion)a).getAppliedEffects().size();j++)
				if(((Champion)a).getAppliedEffects().get(j) instanceof Dodge)
					if(c<0.5)
						return;
					else
						break;
			if((this.getCurrentChampion() instanceof Hero && !((Champion)a instanceof Hero)) || 
				(this.getCurrentChampion() instanceof Villain && !((Champion)a instanceof Villain)) ||
				(this.getCurrentChampion() instanceof AntiHero && !((Champion)a instanceof AntiHero)))
				((Champion)a).setCurrentHP((int)((Champion)a).getCurrentHP()-(int)(this.getCurrentChampion().getAttackDamage()*1.5));
			else	
				((Champion)a).setCurrentHP((int)((Champion)a).getCurrentHP()-(int)this.getCurrentChampion().getAttackDamage());
				if(((Champion) a).getCurrentHP()<=0) {
				((Champion) a).setCondition(Condition.KNOCKEDOUT);
				remover((Champion)a);
			}
		}
	}
	
	public void remover(Damageable c) {
		board[c.getLocation().x][c.getLocation().y] = null;
		if (c instanceof Champion) {
			if(firstPlayer.getTeam().contains(c))
				firstPlayer.getTeam().remove(c);
			if(secondPlayer.getTeam().contains(c))
				secondPlayer.getTeam().remove(c);
			ArrayList<Champion> h = new ArrayList<Champion>();
			int size = turnOrder.size();
			for(int i=0;i<size;i++) {
				Champion p = (Champion)turnOrder.remove();
				if(!c.equals(p))
					h.add(p);	
			}
			while(!h.isEmpty())
				turnOrder.insert(h.remove(0));
		}
	}

	public void castAbility(Ability a) throws AbilityUseException,NotEnoughResourcesException,CloneNotSupportedException {
		ArrayList<Damageable> l = new ArrayList<Damageable>();
		if(this.getCurrentChampion().getCurrentActionPoints()<a.getRequiredActionPoints() || this.getCurrentChampion().getMana()<a.getManaCost())
			throw new NotEnoughResourcesException("Not enough resources");
		if(this.getCurrentChampion().getCondition().equals(Condition.INACTIVE))
			throw new AbilityUseException("Cannot use ability, champion inactive");
		for(int i=0;i<this.getCurrentChampion().getAppliedEffects().size();i++)
			if(this.getCurrentChampion().getAppliedEffects().get(i) instanceof Silence)
				throw new AbilityUseException("Cannot use ability, champion silenced");
		if (a.getCurrentCooldown()>0)
			throw new AbilityUseException("Cannot use ability, cooling down");
		Point p = this.getCurrentChampion().getLocation();
		ArrayList<Point> d = new ArrayList<Point>();
		if(a.getCastArea().equals(AreaOfEffect.SELFTARGET))
			l.add(this.getCurrentChampion());
		else if(a.getCastArea().equals(AreaOfEffect.TEAMTARGET)) {
			l.addAll(firstPlayer.getTeam());
			l.addAll(secondPlayer.getTeam());
			for (int i=0;i<l.size();i++)
				if (distance(l.get(i).getLocation().x,l.get(i).getLocation().y)>a.getCastRange() || !valid(a,l.get(i))) {
					l.remove(i);
					i--;
				}
		}
		else if(a.getCastArea().equals(AreaOfEffect.SURROUND)) {
			Point n = new Point(p.x+1,p.y);
			Point b = new Point(p.x-1,p.y);
			Point w = new Point(p.x,p.y-1);
			Point e = new Point(p.x,p.y+1);
			Point nw = new Point(p.x+1,p.y-1);
			Point ne = new Point(p.x+1,p.y+1);
			Point sw = new Point(p.x-1,p.y-1);
			Point se = new Point(p.x-1,p.y+1);
			d.add(n); d.add(b); d.add(w); d.add(e); d.add(nw); d.add(ne); d.add(sw); d.add(se);
			for(Point c : d)
				if(onBoard(c))
					if(valid(a,board[c.x][c.y]))
						l.add((Damageable)board[c.x][c.y]);
		}
		a.execute(l);
		for (int i=0;i<l.size();i++)
			if (((Damageable)l.get(i)).getCurrentHP()<=0)
				remover(l.get(i));
		this.getCurrentChampion().setCurrentActionPoints(this.getCurrentChampion().getCurrentActionPoints()-a.getRequiredActionPoints());
		this.getCurrentChampion().setMana(this.getCurrentChampion().getMana()-a.getManaCost());
		a.setCurrentCooldown(a.getBaseCooldown());
	}
	
	public boolean onBoard (Point p) {
		return p.x<=4 && p.x>=0 && p.y<=4 && p.y>=0;
	}
	
	public void castAbility(Ability a,Direction d) throws AbilityUseException,NotEnoughResourcesException,CloneNotSupportedException {
		ArrayList<Damageable> l = new ArrayList<Damageable>();
		if(this.getCurrentChampion().getCurrentActionPoints()<a.getRequiredActionPoints() || this.getCurrentChampion().getMana()<a.getManaCost())
			throw new NotEnoughResourcesException("Not enough resources");
		if(this.getCurrentChampion().getCondition().equals(Condition.INACTIVE))
			throw new AbilityUseException("Cannot use ability, champion inactive");
		for(int i=0;i<this.getCurrentChampion().getAppliedEffects().size();i++)
			if(this.getCurrentChampion().getAppliedEffects().get(i) instanceof Silence)
				throw new AbilityUseException("Cannot use ability, champion silenced");
		if (a.getCurrentCooldown()>0)
			throw new AbilityUseException("Cannot use ability, cooling down");
		this.getCurrentChampion().setCurrentActionPoints(this.getCurrentChampion().getCurrentActionPoints()-a.getRequiredActionPoints());
		this.getCurrentChampion().setMana(this.getCurrentChampion().getMana()-a.getManaCost());
		a.setCurrentCooldown(a.getBaseCooldown());
		int r = a.getCastRange();
		Point p = this.getCurrentChampion().getLocation();
		Point t = null;
		Object s = null;
		if(d.equals(Direction.UP)) {
			for(int i=1;i<=r;i++) {
				if(p.x-i<0)
					break;
				t = new Point((p.x-i),(p.y));
				s = board[t.x][t.y];
				if(valid(a,s))
					l.add((Damageable)s);
			}
		}
		else if(d.equals(Direction.DOWN)) {
			for(int i=1;i<=r;i++) {
				if(p.x+i>4)
					break;
				t = new Point((p.x+i),(p.y));
				s = board[t.x][t.y];
				if(valid(a,s)) {
					l.add((Damageable)s);
				}
			}
		}
		else if(d.equals(Direction.LEFT)) {
			for(int i=1;i<=r;i++) {
				if(p.y-i<0)
					break;
				t = new Point((p.x),(p.y-i));
				s = board[t.x][t.y];
				if(valid(a,s))
					l.add((Damageable)s);
			}
		}
		else {
			for(int i=1;i<=r;i++) {
				if(p.y+i>4)
					break;
				t = new Point((p.x),(p.y+i));
				s = board[t.x][t.y];
				if(valid(a,s))
					l.add((Damageable)s);
			}
		}
			a.execute(l);
			for (int i=0;i<l.size();i++)
				if (((Damageable)l.get(i)).getCurrentHP()<=0)
					remover(l.get(i));
	}
	
	public boolean valid(Ability a,Object t) {
		if (t==null)
			return false;
		boolean f = true;
		if(a instanceof DamagingAbility) {
			if(ally(t))
				f = false;
			if (t instanceof Champion)
				for(int j=0;j<((Champion)t).getAppliedEffects().size();j++)
					if(((Champion)t).getAppliedEffects().get(j) instanceof Shield) {
						Effect e = ((Champion)t).getAppliedEffects().get(j);
						((Champion)t).getAppliedEffects().remove(e);
						e.remove((Champion)t);
						f = false;
						break;
					}
		}
		else if(a instanceof HealingAbility) {
			if(enemy(t) || t instanceof Cover)
					f = false;
		}
		else {
			CrowdControlAbility cc = (CrowdControlAbility) a;
			if(t instanceof Cover || (cc.getEffect().getType()==EffectType.BUFF && enemy(t)) || (cc.getEffect().getType()==EffectType.DEBUFF && ally(t)))
				f = false;
		}
		return f;
	}
	
	public boolean validx(Ability a,Object t) {
		if (t==null)
			return false;
		boolean f = true;
		if(a instanceof DamagingAbility) {
			if(ally(t))
				f = false;
		}
		else if(a instanceof HealingAbility) {
			if(enemy(t) || t instanceof Cover)
					f = false;
		}
		else {
			CrowdControlAbility cc = (CrowdControlAbility) a;
			if(t instanceof Cover || (cc.getEffect().getType()==EffectType.BUFF && enemy(t)) || (cc.getEffect().getType()==EffectType.DEBUFF && ally(t)))
				f = false;
		}
		return f;
	}
	
	public boolean ally(Object t) {
		return (firstPlayer.getTeam().contains(t) && firstPlayer.getTeam().contains(this.getCurrentChampion()) || (secondPlayer.getTeam().contains(t) && secondPlayer.getTeam().contains(this.getCurrentChampion())));
	}
	
	public boolean enemy(Object t) {
		return (!(firstPlayer.getTeam().contains(t) && firstPlayer.getTeam().contains(this.getCurrentChampion()))) && (!(secondPlayer.getTeam().contains(t) && secondPlayer.getTeam().contains(this.getCurrentChampion())));
	}
	
	public int distance(int x, int y) {
		return Math.abs(this.getCurrentChampion().getLocation().x-x) + Math.abs(this.getCurrentChampion().getLocation().y-y);
	}
	
	public void castAbility(Ability a,int x,int y) throws AbilityUseException,InvalidTargetException,NotEnoughResourcesException,CloneNotSupportedException {
		Object t = board[x][y];
		ArrayList<Damageable> l = new ArrayList<Damageable>();
		int m = distance(x,y);
		for(int i=0;i<this.getCurrentChampion().getAppliedEffects().size();i++)
			if(this.getCurrentChampion().getAppliedEffects().get(i) instanceof Silence)
				throw new AbilityUseException("Cannot use ability, champion silenced");
		if(this.getCurrentChampion().getCurrentActionPoints()<a.getRequiredActionPoints() || this.getCurrentChampion().getMana()<a.getManaCost())
			throw new NotEnoughResourcesException("Not enough resources");
		if (a.getCurrentCooldown()>0)
			throw new AbilityUseException("Cannot use ability, cooling down");
		if(t==null)
			throw new InvalidTargetException("Invalid target");
		if(this.getCurrentChampion().getCondition().equals(Condition.INACTIVE))
			throw new AbilityUseException("Cannot use ability, champion inactive");
		if(a.getCastRange()<m)
			throw new AbilityUseException("Cannot use ability, target out of range");
		boolean f = validx(a,t);
		if(!f)
			throw new InvalidTargetException("Invalid target");
		this.getCurrentChampion().setCurrentActionPoints(this.getCurrentChampion().getCurrentActionPoints()-a.getRequiredActionPoints());
		this.getCurrentChampion().setMana(this.getCurrentChampion().getMana()-a.getManaCost());
		a.setCurrentCooldown(a.getBaseCooldown());
		if (t instanceof Champion)
			for(int j=0;j<((Champion)t).getAppliedEffects().size();j++)
				if(((Champion)t).getAppliedEffects().get(j) instanceof Shield) {
					Effect e = ((Champion)t).getAppliedEffects().get(j);
					((Champion)t).getAppliedEffects().remove(e);
					e.remove((Champion)t);
					return;
				}
		l.add((Damageable)t);
		a.execute(l);
		if(((Damageable)t).getCurrentHP()<=0)
			remover((Damageable)t);
	}
	
	public void useLeaderAbility() throws LeaderNotCurrentException,LeaderAbilityAlreadyUsedException {
		if((!(getCurrentChampion()).equals(firstPlayer.getLeader())) && (!(getCurrentChampion()).equals(secondPlayer.getLeader())))
			throw new LeaderNotCurrentException("Current champion is not the leader");
		if(firstPlayer.getTeam().contains(getCurrentChampion()) && this.isFirstLeaderAbilityUsed())
			throw new LeaderAbilityAlreadyUsedException("Leader ability already used");
		if(secondPlayer.getTeam().contains(getCurrentChampion()) && this.isSecondLeaderAbilityUsed())
			throw new LeaderAbilityAlreadyUsedException("Leader ability already used");
		if(firstPlayer.getTeam().contains(getCurrentChampion())) firstLeaderAbilityUsed = true;
		if(secondPlayer.getTeam().contains(getCurrentChampion())) secondLeaderAbilityUsed = true;
		ArrayList<Champion> targets = new ArrayList<Champion>();
		if(getCurrentChampion() instanceof Hero)
			if(firstPlayer.getTeam().contains(getCurrentChampion()))
				for(Champion c : firstPlayer.getTeam())
					targets.add(c);
			else
				for(Champion c : secondPlayer.getTeam())
					targets.add(c);
		else if(getCurrentChampion() instanceof Villain) {
			if(firstPlayer.getTeam().contains(getCurrentChampion())) {
				for(Champion c : secondPlayer.getTeam())
					if (c.getCurrentHP() < 0.3 * c.getMaxHP())
						targets.add(c);
			}
			else
				for(Champion c : firstPlayer.getTeam())
					if (c.getCurrentHP() < 0.3 * c.getMaxHP())
						targets.add(c);
		}
		else if(getCurrentChampion() instanceof AntiHero) {
			for(Champion c : firstPlayer.getTeam())
				if(!c.equals(firstPlayer.getLeader()))
					targets.add(c);
			for(Champion c : secondPlayer.getTeam())
				if(!c.equals(secondPlayer.getLeader()))
					targets.add(c);
		}
		getCurrentChampion().useLeaderAbility(targets);
	}
	
	public void endTurn() {
		turnOrder.remove();
		if(turnOrder.isEmpty())
			prepareChampionTurns();		
		boolean f = true;
		while (f) {
			Champion c = this.getCurrentChampion();
			if (c.getCondition().equals(Condition.INACTIVE))
				turnOrder.remove();
			else
				f = false;
			for(Ability a : c.getAbilities())
				a.setCurrentCooldown(a.getCurrentCooldown()-1);
			for(int i=0;i<c.getAppliedEffects().size();i++) {
				c.getAppliedEffects().get(i).setDuration(c.getAppliedEffects().get(i).getDuration()-1);
				Effect e = c.getAppliedEffects().get(i);
				if(e.getDuration()<=0) {
					c.getAppliedEffects().remove(e);
					e.remove(c);
					i--;
				}
			}
			c.setCurrentActionPoints(c.getMaxActionPointsPerTurn());
		}
	}
	
	private void prepareChampionTurns() {
		for(Champion c : firstPlayer.getTeam())
			if(!c.getCondition().equals(Condition.KNOCKEDOUT))
				turnOrder.insert(c);
		for(Champion c : secondPlayer.getTeam())
			if(!c.getCondition().equals(Condition.KNOCKEDOUT))
				turnOrder.insert(c);
	}
	
	public boolean hasEffect(Champion currentChampion, String s) {
		for (Effect e : currentChampion.getAppliedEffects()) {
			if (e.getName().equals(s))
				return true;
		}
		return false;
	}
}
