package cat.xojan.random1.domain.interactor

import android.content.res.Resources
import android.net.Uri
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserServiceCompat
import android.support.v4.media.MediaDescriptionCompat
import android.util.Log
import cat.xojan.random1.domain.entities.Program
import cat.xojan.random1.other.MediaIDHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MusicProvider @Inject constructor(val programInteractor: ProgramDataInteractor) {

    private val TAG = MusicProvider::class.simpleName

    fun retrieveMedia(
            result: MediaBrowserServiceCompat.Result<MutableList<MediaBrowserCompat.MediaItem>>,
            parentId: String,
            resources: Resources) {
        if (parentId == MediaIDHelper.MEDIA_ID_ROOT) {
            programInteractor.loadPrograms()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { n -> handleSuccess(n, result) },
                            { e -> handleError(e) },
                            { handleComplete() }
                    )
        } else {
            /*var subscription = programInteractor.getCurrentPodcasts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ this.updateView(it, result) },
                            { this.handleError(it) },
                            { this.handleComplete()})*/
            result.sendResult(arrayListOf())
        }
    }

    private fun handleSuccess(
            programs: List<Program>,
            result: MediaBrowserServiceCompat.Result<MutableList<MediaBrowserCompat.MediaItem>>) {
        Log.d(TAG, "Retrieve programs success")
        val mediaItems = programs.mapTo(ArrayList()) { createBrowsableMediaItemForProgram(it) }
        result.sendResult(mediaItems)
    }

    private fun handleComplete() {

    }

    private fun handleError(it: Throwable?) {
        Log.e(TAG, it.toString())
    }

    private fun createBrowsableMediaItemForProgram(program: Program): MediaBrowserCompat.MediaItem {
        val description = MediaDescriptionCompat.Builder()
                .setMediaId(program.id)
                .setTitle(program.title)
                .setIconUri(Uri.parse(program.imageUrl()))
                .build()
        return MediaBrowserCompat.MediaItem(description,
                MediaBrowserCompat.MediaItem.FLAG_BROWSABLE)
    }
}