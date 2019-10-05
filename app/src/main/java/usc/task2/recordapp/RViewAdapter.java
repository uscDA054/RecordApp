package usc.task2.recordapp;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class RViewAdapter extends RecyclerView.Adapter<RViewAdapter.ViewHolder> {
    private Context context;
    private List<Logs> pantryItems;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RViewAdapter(Context context, List<Logs> pantryItems) {
        this.context = context;
        this.pantryItems = pantryItems;
    }

    @Override
    public RViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(RViewAdapter.ViewHolder holder, int position) {

        Logs pantry = pantryItems.get(position);

        holder.title.setText(pantry.getTitle());
        holder.place.setText(pantry.getPlace());
        holder.dateAdded.setText(pantry.getLogDt());
        holder.longitude.setText(pantry.getLongitude());
        holder.latitude.setText(pantry.getLatitude());

    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView place;
        public TextView dateAdded;
        public TextView details;
        public TextView longitude;
        public TextView latitude;
        public Button deleteButton;




        public ViewHolder(View view, Context ctx) {
            super(view);

            context = ctx;

            title = (TextView) view.findViewById(R.id.title);
            place = (TextView) view.findViewById(R.id.place);
            details = (TextView) view.findViewById(R.id.details);
            longitude = (TextView) view.findViewById(R.id.longitude);
            latitude = (TextView) view.findViewById(R.id.latitude);
            dateAdded = (TextView) view.findViewById(R.id.dateAdded);
            deleteButton = (Button) view.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(this);

//////////////////start details activity with content/////////////

         view.setOnClickListener(new View.OnClickListener() {
              @Override
           public void onClick(View v) {
//                    //go to next screen/ DetailsActivity
                /*  int position = getAdapterPosition();

                  final LogRecord grocery = pantryItems.get(position);
                  LayoutInflater li = LayoutInflater.from(context);
                  View promptsView = li.inflate(R.layout.prompts, null);

                  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                          context);

                  // set prompts.xml to alertdialog builder
                  alertDialogBuilder.setView(promptsView);

                  final EditText userInput = (EditText) promptsView
                          .findViewById(R.id.editTextDialogUserInput);
                  final TextView itm = (TextView) promptsView
                          .findViewById(R.id.textItem);
                  itm.setText(grocery.getTitle());
                  // set dialog message
                  alertDialogBuilder
                          .setCancelable(false)
                          .setPositiveButton("OK",
                                  new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog,int id) {
                                          ArrayList<S> items = ShopCart.Instance().getItemList();
                                          // get user input and set it to result
                                          // edit text
                                          int qty = Integer.parseInt(grocery.getQuantity().substring(5));
                                         if(qty>0)
                                         {
                                            items.add(new ShopItem(grocery.getName(),qty)) ;
                                         }
                                      }
                                  })
                          .setNegativeButton("Cancel",
                                  new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog,int id) {
                                          dialog.cancel();
                                      }
                                  });

                  // create alert dialog
                  AlertDialog alertDialog = alertDialogBuilder.create();

                  // show it
                  alertDialog.show();
                ///////////////////////////////////////

                  Intent intent = new Intent(context, DetailsActivity.class);
                 intent.putExtra("name", grocery.getName());
                   intent.putExtra("quantity", grocery.getQuantity());
                  intent.putExtra("id", grocery.getId());
                    intent.putExtra("date", grocery.getDateItemAdded());*/
                  // context.startActivity(intent);


             }
            });
        }



        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.deleteButton:
                   int position = getAdapterPosition();
                    Logs logrec = pantryItems.get(position);
                    deleteItem(logrec.getTitle());

                    break;


            }
        }

        public void deleteItem(final String id) {

            //create an AlertDialog
            alertDialogBuilder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirm_dialog, null);

            Button noButton = (Button) view.findViewById(R.id.noButton);
            Button yesButton = (Button) view.findViewById(R.id.yesButton);

            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();


            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //delete the item.
                    SQLiteHelper db = new SQLiteHelper(context);
                    //delete item
                    db.deleteLog(id);
                    pantryItems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();


                }
            });

        }



    }

}
