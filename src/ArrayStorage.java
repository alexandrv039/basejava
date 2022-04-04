/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size;

    void clear() {
        storage = new Resume[10000];
        size = 0;
    }

    void save(Resume r) {
        if (size == 10000) {
            // ??? Exception?
        }
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        Resume r = null;
        for (int i = 0; i < size; i++) {
            Resume resume = storage[i];
            if (resume == null) {
                continue;
            } else if (resume.uuid.equals(uuid)) {
                r = resume;
                break;
            }

        }
        return r;
    }

    void delete(String uuid) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i] == null) {
                continue;
            } else if (storage[i].uuid.equals(uuid)) {
                index = i;
                size--;
                break;
            }
        }

        if (index > -1) {
            for (int i = index; i < size; i++) {
                Resume r = storage[i + 1];
                storage[i] = r;
            }
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    int size() {
        return size;
    }
}
