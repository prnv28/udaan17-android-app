package in.udaan17.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.Space;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import in.udaan17.android.R;
import in.udaan17.android.adapter.ManagerAdapter;
import in.udaan17.android.model.Event;
import in.udaan17.android.util.Helper;
import in.udaan17.android.util.listeners.ListItemClickCallBack;

public class EventDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Event event;

    private AppCompatTextView textViewDescriptionLabel;
    private AppCompatTextView textViewDescription;
    private Space spaceDescriptionParticipants;
    private AppCompatTextView textViewParticipantsLabel;
    private AppCompatTextView textViewParticipants;
    private Space spaceParticipantsRounds;
    private AppCompatTextView textViewRoundsLabel;
    private AppCompatTextView textViewRounds;
    private Space spaceRoundsFees;
    private AppCompatTextView textViewFeesLabel;
    private AppCompatTextView textViewFees;
    private Space spaceFeesContact;
    private AppCompatButton buttonContact;
    private AppCompatImageView departmentImageView;
    private AppCompatTextView textViewPrize;
    private AppCompatTextView textViewPrizeLabel;
    private Space spacePrize;
    private String resourceName;

    public static void startActivity(Activity activity, String fileName, Event event) {
        Intent intent = new Intent(activity, EventDetailsActivity.class);
        intent.putExtra(activity.getString(R.string.activity_key_event_data), event.toString());
        intent.putExtra(activity.getString(R.string.activity_key_title_name), fileName);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_event_details);

        departmentImageView = (AppCompatImageView) findViewById(R.id.appbar_image_view_main);
        event = Event.parseJson(this.getIntent().getStringExtra(this.getString(R.string.activity_key_event_data)));
        resourceName = Helper.getResourceNameFromTitle(this.getIntent().getStringExtra(this.getString(R.string.activity_key_title_name)));

        this.initializeElements();
        this.populateUI();
    }

    private void initializeElements() {
        int resourceId = getResources().getIdentifier(this.resourceName + "land", "drawable", getPackageName());
        departmentImageView.setImageResource(resourceId);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.appbar_toolbar);
        this.setSupportActionBar(toolbar);
        if (this.getSupportActionBar() != null) {
            ActionBar actionBar = this.getSupportActionBar();
            actionBar.setTitle(this.event.getEventName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        this.textViewDescriptionLabel = (AppCompatTextView) this.findViewById(R.id.text_view_event_details_description_label);
        this.textViewDescription = (AppCompatTextView) this.findViewById(R.id.text_view_event_details_description);
        this.spaceDescriptionParticipants = (Space) this.findViewById(R.id.space_event_details_description_participants);
        this.textViewParticipantsLabel = (AppCompatTextView) this.findViewById(R.id.text_view_event_details_participants_label);
        this.textViewParticipants = (AppCompatTextView) this.findViewById(R.id.text_view_event_details_participants);
        this.spaceParticipantsRounds = (Space) this.findViewById(R.id.space_event_details_participants_rounds);
        this.textViewRoundsLabel = (AppCompatTextView) this.findViewById(R.id.text_view_event_details_rounds_label);
        this.textViewRounds = (AppCompatTextView) this.findViewById(R.id.text_view_event_details_rounds);
        this.spaceRoundsFees = (Space) this.findViewById(R.id.space_event_details_rounds_fees);
        this.textViewFeesLabel = (AppCompatTextView) this.findViewById(R.id.text_view_event_details_fees_label);
        this.textViewFees = (AppCompatTextView) this.findViewById(R.id.text_view_event_details_fees);
        this.spaceFeesContact = (Space) this.findViewById(R.id.space_event_details_fees_contact);
        this.buttonContact = (AppCompatButton) this.findViewById(R.id.button_event_details_contact);

        this.textViewPrizeLabel = (AppCompatTextView) this.findViewById(R.id.text_view_event_details_prize_label);
        this.textViewPrize = (AppCompatTextView) this.findViewById(R.id.text_view_event_details_prize);
        this.spacePrize = (Space) this.findViewById(R.id.space_event_details_fees_contact1);


        this.buttonContact.setOnClickListener(this);

        this.buttonContact.setBackgroundColor(ContextCompat.getColor(this, this.getResources().getIdentifier("color_" + this.resourceName, "color", this.getPackageName())));

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/udaanRegular.ttf");

        textViewDescription.setTypeface(custom_font);
        textViewParticipants.setTypeface(custom_font);
        textViewRounds.setTypeface(custom_font);
        textViewFees.setTypeface(custom_font);
        textViewPrize.setTypeface(custom_font);
    }

    private void populateUI() {
        if (this.event.getEventDescription() != null && this.event.getEventDescription().length() > 0) {
            this.textViewDescription.setText(this.event.getEventDescription());
        } else {
            this.textViewDescriptionLabel.setVisibility(View.GONE);
            this.textViewDescription.setVisibility(View.GONE);
        }

        if (this.event.getParticipants() != null && this.event.getParticipants().length() > 0) {
            this.textViewParticipants.setText(this.event.getParticipants());
        } else {
            this.spaceDescriptionParticipants.setVisibility(View.GONE);
            this.textViewParticipantsLabel.setVisibility(View.GONE);
            this.textViewParticipants.setVisibility(View.GONE);
        }
    
        if (this.event.getRoundsDescription().length() > 0) {
            this.textViewRounds.setText(this.event.getRoundsDescription());
        } else {
            this.spaceParticipantsRounds.setVisibility(View.GONE);
            this.textViewRoundsLabel.setVisibility(View.GONE);
            this.textViewRounds.setVisibility(View.GONE);
        }

        if (this.event.getFees() != null && this.event.getFees().length() > 0) {
            this.textViewFees.setText(this.getString(R.string.symbol_rupee) + " " + this.event.getFees());
        } else {
            this.spaceRoundsFees.setVisibility(View.GONE);
            this.textViewFeesLabel.setVisibility(View.GONE);
            this.textViewFees.setVisibility(View.GONE);
        }
    
        if (!(this.event.getEventManagers() != null && this.event.getEventManagers().size() > 0)) {
            this.spaceFeesContact.setVisibility(View.GONE);
            this.buttonContact.setVisibility(View.GONE);
        }
    
        if (this.event.getPrizeDescription(this.getString(R.string.symbol_rupee)).length() > 0) {
            this.textViewPrize.setText(this.event.getPrizeDescription(this.getString(R.string.symbol_rupee)));
        } else {
            this.spacePrize.setVisibility(View.GONE);
            this.textViewPrizeLabel.setVisibility(View.GONE);
            this.textViewPrize.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button_event_details_contact:
                this.showManagersDialog();
                break;
        }
    }

    private void showManagersDialog() {
        ManagerAdapter adapter = new ManagerAdapter(this.event.getEventManagers(),
                this.resourceName,
                this,
                new ListItemClickCallBack() {
                    @Override
                    public void onItemClick(int position, int viewId) {
                        Helper.makeCall(
                                EventDetailsActivity.this.event.getEventManagers().get(position).getContactInfo(),
                                EventDetailsActivity.this
                        );
                    }
                }
        );

        MaterialDialog dialog =
                new MaterialDialog.Builder(this)
                        .title("Event Managers")
                        .adapter(adapter, new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                        .cancelable(true)
                        .build();

        dialog.show();
    }
}
