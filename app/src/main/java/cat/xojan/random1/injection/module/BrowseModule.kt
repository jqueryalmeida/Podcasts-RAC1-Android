package cat.xojan.random1.injection.module

import android.app.Activity
import cat.xojan.random1.domain.interactor.PodcastDataInteractor
import cat.xojan.random1.domain.interactor.ProgramDataInteractor
import cat.xojan.random1.domain.model.EventLogger
import cat.xojan.random1.feature.browser.BrowserViewModel
import dagger.Module
import dagger.Provides

@Module
class BrowseModule(private val activity: Activity) {

    @Provides
    fun provideBrowserViewModel(
            podcastInteractor: PodcastDataInteractor,
            programInteractor: ProgramDataInteractor,
            eventLogger: EventLogger
            ): BrowserViewModel {
        return BrowserViewModel(podcastInteractor, programInteractor, eventLogger)
    }
}