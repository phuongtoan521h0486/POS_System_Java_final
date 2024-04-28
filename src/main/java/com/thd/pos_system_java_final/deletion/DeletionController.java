package com.thd.pos_system_java_final.deletion;

public abstract class DeletionController {
    public String delete(int id) {
        deleteEntity(id);
        return redirectToLink();
    }

    protected abstract void deleteEntity(int id);

    protected abstract String redirectToLink();
}
