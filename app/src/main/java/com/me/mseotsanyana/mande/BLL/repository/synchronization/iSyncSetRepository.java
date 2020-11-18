package com.me.mseotsanyana.mande.BLL.repository.synchronization;

import java.util.ArrayList;
import java.util.Set;

public interface iSyncSetRepository<T, K> {
    void synchronizeEntitySet(ArrayList<T> entitySet);
}
