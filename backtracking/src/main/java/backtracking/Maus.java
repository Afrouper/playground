package backtracking;

public class Maus {
	public Labyrinth lab = new Labyrinth();

	public Maus() {
		lab.ausgeben();
		sucheKaese(1, 0);
	}

	public void sucheKaese(int posX, int posY) {

		if (lab.gefunden(posX, posY)) {
			System.out.println("Käse gefunden");
			lab.ausgeben();
		}
		else {
			lab.setzteMaus(posX, posY, 'M');
			if (lab.rechtsFrei(posX, posY)) {
				sucheKaese(++posX, posY);
			}
			if (lab.linksFrei(posX, posY)) {
				sucheKaese(--posX, posY);
			}
			if (lab.obenFrei(posX, posY)) {
				sucheKaese(posX, --posY);
			}
			if (lab.untenFrei(posX, posY)) {
				sucheKaese(posX, ++posY);
			}
		}
		lab.setzteMaus(posX, posY, ' ');
	}
}
