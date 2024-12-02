import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Fortunately, the first location The Historians want to search isn't a long walk from the Chief
 * Historian's office.
 *
 * <p>While the Red-Nosed Reindeer nuclear fusion/fission plant appears to contain no sign of the
 * Chief Historian, the engineers there run up to you as soon as they see you. Apparently, they
 * still talk about the time Rudolph was saved through molecular synthesis from a single electron.
 *
 * <p>They're quick to add that - since you're already here - they'd really appreciate your help
 * analyzing some unusual data from the Red-Nosed reactor. You turn to check if The Historians are
 * waiting for you, but they seem to have already divided into groups that are currently searching
 * every corner of the facility. You offer to help with the unusual data.
 *
 * <p>The unusual data (your puzzle input) consists of many reports, one report per line. Each
 * report is a list of numbers called levels that are separated by spaces. For example:
 *
 * <p>7 6 4 2 1 1 2 7 8 9 9 7 6 2 1 1 3 2 4 5 8 6 4 4 1 1 3 6 7 9 This example data contains six
 * reports each containing five levels.
 *
 * <p>The engineers are trying to figure out which reports are safe. The Red-Nosed reactor safety
 * systems can only tolerate levels that are either gradually increasing or gradually decreasing.
 * So, a report only counts as safe if both of the following are true:
 *
 * <p>The levels are either all increasing or all decreasing. Any two adjacent levels differ by at
 * least one and at most three. In the example above, the reports can be found safe or unsafe by
 * checking those rules:
 *
 * <p>7 6 4 2 1: Safe because the levels are all decreasing by 1 or 2. 1 2 7 8 9: Unsafe because 2 7
 * is an increase of 5. 9 7 6 2 1: Unsafe because 6 2 is a decrease of 4. 1 3 2 4 5: Unsafe because
 * 1 3 is increasing but 3 2 is decreasing. 8 6 4 4 1: Unsafe because 4 4 is neither an increase or
 * a decrease. 1 3 6 7 9: Safe because the levels are all increasing by 1, 2, or 3. So, in this
 * example, 2 reports are safe.
 *
 * <p>Analyze the unusual data from the engineers. How many reports are safe?
 *
 * <p>--- Part Two --- The engineers are surprised by the low number of safe reports until they
 * realize they forgot to tell you about the Problem Dampener.
 *
 * <p>The Problem Dampener is a reactor-mounted module that lets the reactor safety systems tolerate
 * a single bad level in what would otherwise be a safe report. It's like the bad level never
 * happened!
 *
 * <p>Now, the same rules apply as before, except if removing a single level from an unsafe report
 * would make it safe, the report instead counts as safe.
 *
 * <p>More of the above example's reports are now safe:
 *
 * <p>7 6 4 2 1: Safe without removing any level. 1 2 7 8 9: Unsafe regardless of which level is
 * removed. 9 7 6 2 1: Unsafe regardless of which level is removed. 1 3 2 4 5: Safe by removing the
 * second level, 3. 8 6 4 4 1: Safe by removing the third level, 4. 1 3 6 7 9: Safe without removing
 * any level. Thanks to the Problem Dampener, 4 reports are actually safe!
 *
 * <p>Update your analysis by handling situations where the Problem Dampener can remove a single
 * level from unsafe reports. How many reports are now safe?
 */
public class RedNosedReports {

  // part 1
  public static boolean countSafeReport(int[] array) {
    boolean isDescending = false;
    for (int i = 1; i < array.length; i++) {
      int curr = array[i];
      int prev = array[i - 1];
      int diff = Math.abs(curr - prev);
      if (i == 1) {
        isDescending = curr < prev;
      }
      if (isDescending && prev <= curr || !isDescending && prev >= curr) {
        return false;
      } else if (diff < 1 || diff > 3) {
        return false;
      }
    }
    return true;
  }

  // part 2
  private static int[][] getReportsWithOneLevelRemoved(int[] array) {
    final int[][] reports = new int[array.length][array.length - 1];
    for (int i = 0; i < array.length; i++) {
      final int[] report = new int[array.length - 1];
      int idx = 0;
      for (int j = 0; j < array.length; j++) {
        if (j != i) {
          report[idx] = array[j];
          idx++;
        }
      }
      reports[i] = report;
    }
    return reports;
  }

  private static boolean isSafeWithMaxOneLevelRemoved(int[] report) {
    if (countSafeReport(report)) {
      return true;
    } else {
      for (final int[] r : getReportsWithOneLevelRemoved(report)) {
        if (countSafeReport(r)) {
          return true;
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {
    List<int[]> list1 = new ArrayList<>();
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    InputStream is = loader.getResourceAsStream("rednosedreportsinput.txt");
    Scanner scanner = new Scanner(is);
    while (scanner.hasNextLine()) {
      int[] numbers =
          Arrays.stream(scanner.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();
      list1.add(numbers);
    }
    long safeReports = list1.stream().filter(RedNosedReports::countSafeReport).count();
    System.out.println(safeReports);

    long toleratedSafeReports =
        list1.stream().filter(RedNosedReports::isSafeWithMaxOneLevelRemoved).count();
    System.out.println(toleratedSafeReports);
  }
}
