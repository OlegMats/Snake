import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Records {
	private final ArrayList<Integer> list = new ArrayList<Integer>();

	public Records() {

	}

	public Records(int score) {
		readingRecord();
		writeRecord(addingRecord(score));
	}

	public ArrayList<Integer> addingRecord(int score) {
		if (list.size() < 10) {
			list.add(score);
		} else if (Collections.min(list) < score) {
			list.remove(Collections.min(list));
			list.add(score);
		}
		Collections.sort(list);
		return list;
	}

	public void writeRecord(ArrayList<Integer> list) {
		try (FileWriter writer = new FileWriter("records.txt", false)) {
			for (int i : list) {
				writer.write(i + "\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public ArrayList<Integer> readingRecord() {
		String i;
		try (BufferedReader reader = new BufferedReader(new FileReader("records.txt"))) {
			while ((i = reader.readLine()) != null) {
				list.add(Integer.parseInt(i));
			}
			reader.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return list;
	}

}
