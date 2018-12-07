package com.gemvietnam.timekeeping.interfaces;

import java.util.List;

/**
 * Created by root on 27/03/2018.
 */

public interface ResultsView {
    void setResults(final List<Classifier.Recognition> results);
}
