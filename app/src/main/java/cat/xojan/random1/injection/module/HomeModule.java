package cat.xojan.random1.injection.module;

import android.app.Activity;
import android.app.DownloadManager;

import cat.xojan.random1.domain.interactor.ProgramDataInteractor;
import cat.xojan.random1.injection.PerActivity;
import cat.xojan.random1.presenter.DownloadsPresenter;
import cat.xojan.random1.presenter.HomePresenter;
import cat.xojan.random1.presenter.PodcastListPresenter;
import cat.xojan.random1.presenter.ProgramsPresenter;
import cat.xojan.random1.presenter.SectionPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    private final Activity mActivity;

    public HomeModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    PodcastListPresenter provideLatestPodcastPresenter(ProgramDataInteractor programDataInteractor,
                                                       DownloadManager downloadManager) {
        return new PodcastListPresenter(programDataInteractor, mActivity, downloadManager);
    }

    @Provides @PerActivity
    ProgramsPresenter provideProgramsPresenter(ProgramDataInteractor programDataInteractor) {
        return new ProgramsPresenter(programDataInteractor);
    }

    @Provides @PerActivity
    DownloadsPresenter provideDownloadsPresenter(ProgramDataInteractor programDataInteractor) {
        return new DownloadsPresenter(programDataInteractor);
    }

    @Provides
    SectionPresenter provideSectionPresenter(ProgramDataInteractor programDataInteractor) {
        return new SectionPresenter(programDataInteractor);
    }

    @Provides
    @PerActivity
    HomePresenter provideHomePresenter(ProgramDataInteractor programDataInteractor) {
        return new HomePresenter(mActivity, programDataInteractor);
    }
}
