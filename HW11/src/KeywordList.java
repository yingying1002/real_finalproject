import java.util.ArrayList;

public class KeywordList {
	private ArrayList<Keyword> lst;

	public KeywordList() {
		this.lst = new ArrayList<Keyword>();
	}

	public void add(Keyword keyword) {
		lst.add(keyword);
//  System.out.println("Done");
	}

	// quick sort
	public void sort() {
		if (lst.size() == 0) {
			System.out.println("InvalidOperation");
		} else {
			quickSort(0, lst.size() - 1);
//   System.out.println("Done");
		}

	}

	private void quickSort(int leftbound, int rightbound) {
		// 1. implement quickSort algorithm
		if (leftbound >= rightbound) {
			return;
		} else {
			int pivot = rightbound;
			int j = leftbound;
			int k = rightbound - 1;
			while (j < k) {
				while (j < k && lst.get(j).weight < lst.get(pivot).weight) {
					j++;
				}
				while (j < k && lst.get(k).weight > lst.get(pivot).weight) {
					k--;
				}
				if (j < k) {
					swap(j, k);
				}
			}
			swap(j, pivot);
			quickSort(leftbound, j - 1);
			quickSort(j + 1, rightbound);
		}

	}

	private void swap(int aIndex, int bIndex) {
		Keyword temp = lst.get(aIndex);
		lst.set(aIndex, lst.get(bIndex));
		lst.set(bIndex, temp);
	}

	public void output() {
		// TODO: write output and remove all element logic here...
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < lst.size(); i++) {
			Keyword k = lst.get(i);
			if (i > 0)
				sb.append(" ");
			sb.append(k.toString());
		}

		System.out.println(sb.toString());
	}

	public ArrayList<Keyword> getKeywordList() {
		// TODO Auto-generated method stub
		return lst;
	}
}