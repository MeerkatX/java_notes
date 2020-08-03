package bs;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/29
 * @Description:
 * 用到二分查找
 */
public class FindInMountainArray {
    public static void main(String[] args) {
        MountainArray mountainArray = new MountainArray() {
            private int[] i = new int[]{0,1,2,4,2,1};

            @Override
            public int get(int index) {
                return i[index];
            }

            @Override
            public int length() {
                return i.length;
            }
        };
        System.out.println(new FindInMountainArray().findInMountainArray(3, mountainArray));
    }

    public int findInMountainArray(int target, MountainArray mountainArr) {
        int maxIndex = getMaxNum(mountainArr, 0, mountainArr.length() - 1);
        System.out.println(mountainArr.get(maxIndex));
        if (target > mountainArr.get(maxIndex))
            return -1;
        else if (target < mountainArr.get(maxIndex)) {
            int left = binarySearchNormal(mountainArr, 0, maxIndex - 1, target);
            int right = binarySearch(mountainArr, maxIndex + 1, mountainArr.length()-1, target);
            return left != -1 ? left : right;
        } else
            return maxIndex;
    }

    private int binarySearchNormal(MountainArray m, int start, int end, int target) {
        if (start >= end)
            return m.get(start) != target ? -1 : start;
        int mid = start + (end - start) / 2;
        if (m.get(mid) == target)
            return mid;
        if (m.get(mid) < target)
            return binarySearchNormal(m, mid + 1, end, target);
        else
            return binarySearchNormal(m, start, mid - 1, target);
    }

    private int binarySearch(MountainArray m, int start, int end, int target) {
        if (start >= end)
            return m.get(start) != target ? -1 : start;
        int mid = start + (end - start) / 2;
        if (m.get(mid) == target)
            return mid;
        if (m.get(mid) > target)
            return binarySearch(m, mid + 1, end, target);
        else
            return binarySearch(m, start, mid - 1, target);
    }


    private int getMaxNum(MountainArray m, int start, int end) {
        if (start == end) {
            return start;
        }
        int mid = start + (end - start) / 2;
        if (m.get(mid - 1) <= m.get(mid) && m.get(mid) >= m.get(mid + 1))
            return mid;
        else if (m.get(mid - 1) > m.get(mid))
            return getMaxNum(m, start, mid - 1);
        else
            return getMaxNum(m, mid + 1, end);
        //(m.get(mid + 1) > m.get(mid))
    }
}

interface MountainArray {
    int get(int index);

    int length();
}