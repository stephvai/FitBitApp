public class Calc {

	public Calc() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		for (int j = 0; j <= 11; j++) {
			int count = 0;
			for (int i = 0; i >= 0; i--) {
				count ++;
				if ((i %= 2) == 0) {
					i /= 2;
				}
				
				else {
					i--;
				}
			}
			
			System.out.println("n = " + j + ", Stack Frames = " + count);
		}

	}

}
