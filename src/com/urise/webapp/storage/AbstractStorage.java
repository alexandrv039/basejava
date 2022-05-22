package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    protected static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    protected static final Comparator<Resume> RESUME_COMPARATOR = (r1, r2) ->
        Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid).compare(r1, r2);

    public Resume get(String searchString) {
        LOG.info("Get " + searchString);
        SK key = getNotExistKey(searchString);
        return doGet(key);
    }

    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK key = getNotExistKey(resume.getUuid());
        doUpdate(key, resume);
    }

    public void save(Resume r) {
        LOG.info("Save " + r);
        SK key = getExistKey(r.getUuid());
        doSave(key, r);
    }

    public void delete(String searchString) {
        LOG.info("Delete " + searchString);
        SK key = getNotExistKey(searchString);
        doDelete(key);
    }

    private SK getExistKey(String searchString) {
        SK key = getKey(searchString);
        if (isExist(key)) {
            LOG.warning("Resume" + searchString + " is already exist");
            throw new ExistStorageException(searchString);
        }
        return key;
    }

    private SK getNotExistKey(String searchString) {
        SK key = getKey(searchString);
        if (!isExist(key)) {
            LOG.warning("Resume" + searchString + " is not exist");
            throw new NotExistStorageException(searchString);
        }
        return key;
    }

    public List<Resume> getAllSorted(){
        LOG.info("getAllSorted");
        List<Resume> resumes = getAll();
        resumes.sort(RESUME_COMPARATOR);
        return resumes;
    }

    protected abstract List<Resume> getAll();

    protected abstract void doDelete(SK key);

    protected abstract void doSave(SK key, Resume r);

    protected abstract Resume doGet(SK key);

    protected abstract boolean isExist(SK key);

    protected abstract void doUpdate(SK key, Resume resume);

    protected abstract SK getKey(String searchString);
}
