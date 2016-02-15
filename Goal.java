public class Goal{

private int target;
private boolean acheived;

/*This constructor initializes the object and sets the target goal */
public void Goal(){
acheived = false;
}


/*Getters and Setters */
public int getTarget(){
	return target;
}

public void setTarget(int target){
	this.target = target;
}


public boolean getAchieved(){
return acheived;
}

public void setAchieved(boolean acheived){
this.acheived = acheived;
}




}