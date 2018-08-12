package com.github.pgycode.a16.tool;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class WeakTask<T> extends AsyncTask<Void, Void, T> {

    private WeakReference<OnWeakTaskListener<T>> listenerReference;

    public WeakTask(OnWeakTaskListener<T> listener){
        this.listenerReference = new WeakReference<>(listener);
    }
    @Override
    protected T doInBackground(Void... voids) {
        if (listenerReference.get() != null) {
            return listenerReference.get().middle();
        }else{
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listenerReference.get() != null) {
            listenerReference.get().before();
        }
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        if (listenerReference.get() != null && t != null){
            listenerReference.get().after(t);
        }
    }
}