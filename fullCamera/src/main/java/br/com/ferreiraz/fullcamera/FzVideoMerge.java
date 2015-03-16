package br.com.ferreiraz.fullcamera;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sidferreira on 16/03/15.
 */
public class FzVideoMerge implements Runnable {
    private ArrayList<ResultFile> _items;
    private Handler _handler;
    private Runnable _runnable;
    private File _finalFile;

    public FzVideoMerge(ArrayList<ResultFile> items, File finalFile, Handler handler, Runnable runnable) {
        _items = items;
        _finalFile = finalFile;
        _handler = handler;
        _runnable = runnable;
    }

    @Override
    public void run() {

        List<Track> videoTracks = new LinkedList<Track>();
        List<Track> audioTracks = new LinkedList<Track>();

        Movie[] inMovies = new Movie[_items.size()];
        for (int i = 0; i < _items.size(); i++) {
            try {
                inMovies[i] = MovieCreator.build(_items.get(i).getFile().getAbsolutePath());
                for (Track t : inMovies[i].getTracks()) {
                    if (t.getHandler().equals("soun")) {
                        audioTracks.add(t);
                    }
                    if (t.getHandler().equals("vide")) {
                        videoTracks.add(t);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Movie result = new Movie();

            if (audioTracks.size() > 0) {
                result.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
            }
            if (videoTracks.size() > 0) {
                result.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
            }

            Container out = new DefaultMp4Builder().build(result);

            FileChannel fc = new RandomAccessFile(_finalFile.getAbsolutePath(), "rw").getChannel();
            out.writeContainer(fc);
            fc.close();
            FullCameraActivity.logMessage("_finalFile: " + _finalFile.getAbsolutePath() + " >>> " + _finalFile.length());
            _items.add(new ResultFile(_finalFile));
        } catch (Exception e) {
            e.printStackTrace();
        }

        _handler.post(_runnable);
    }
}
