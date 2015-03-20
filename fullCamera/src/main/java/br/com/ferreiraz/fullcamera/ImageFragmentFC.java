package br.com.ferreiraz.fullcamera;

import android.view.View;
import android.widget.AdapterView;

import com.learnncode.mediachooser.MediaModel;
import com.learnncode.mediachooser.fragment.ImageFragment;

public class ImageFragmentFC extends ImageFragment {

    @Override

    protected void setupOnItemClickListener() {
        mImageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                MediaModel galleryModel = mImageAdapter.getItem(position);
                galleryModel.status = !galleryModel.status;
                mImageAdapter.notifyDataSetChanged();

                if (galleryModel.status) {
                    mSelectedItems.add(galleryModel.url.toString());
                } else {
                    mSelectedItems.remove(galleryModel.url.toString().trim());
                }

                if (mCallback != null) {
                    mCallback.onImageSelected(mSelectedItems.size());
                }
            }
        });
    }

    @Override
    protected int getGridLayoutResource() {
        return R.layout.image_fragment_grid;
    }

    public void clearSelection() {
        for (MediaModel mediaModel : mGalleryModelList) {
            mediaModel.status = false;
        }
        mImageAdapter.notifyDataSetChanged();
    }
}
