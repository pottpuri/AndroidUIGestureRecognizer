package it.sephiroth.android.library.uigestures.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import it.sephiroth.android.library.uigestures.*

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity(), UIGestureRecognizer.OnActionListener, UIGestureRecognizerDelegate.Callback {

    private var mRoot: ViewGroup? = null
    private var mDelegate: UIGestureRecognizerDelegate? = null
    private var dateFormat: DateFormat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.US)

        UIGestureRecognizer.logEnabled = BuildConfig.DEBUG

        mDelegate = UIGestureRecognizerDelegate(this)

        val recognizer1 = UITapGestureRecognizer(this)
        recognizer1.tapsRequired = 1
        recognizer1.touchesRequired = 1
        recognizer1.tag = "single-tap"
        recognizer1.tapTimeout = 200
        recognizer1.actionListener = this

        val recognizer2 = UITapGestureRecognizer(this)
        recognizer2.tag = "double-tap"
        recognizer2.tapsRequired = 2
        recognizer2.touchesRequired = 1
        recognizer2.actionListener = this

        val recognizer3 = UILongPressGestureRecognizer(this)
        recognizer3.tag = "long-press"
        recognizer3.tapsRequired = 0
        recognizer3.touchesRequired = 1
        recognizer3.actionListener = this

        val recognizer4 = UILongPressGestureRecognizer(this)
        recognizer4.tag = "long-press-2"
        recognizer4.tapsRequired = 0
        recognizer4.minimumPressDuration = 4000
        recognizer4.allowableMovement = 500f
        recognizer4.actionListener = this

        val recognizer5 = UIPanGestureRecognizer(this)
        recognizer5.tag = "pan"
        recognizer5.actionListener = this
        recognizer5.minimumNumberOfTouches = 2
        recognizer5.maximumNumberOfTouches = 5

        val recognizer6 = UIPinchGestureRecognizer(this)
        recognizer6.tag = "pinch"
        recognizer6.actionListener = this

        val recognizer7 = UIRotateGestureRecognizer(this)
        recognizer7.tag = "rotation"
        recognizer7.actionListener = this

        val recognizer8 = UISwipeGestureRecognizer(this)
        recognizer8.tag = "swipe"
        recognizer8.actionListener = this
        recognizer8.direction = UISwipeGestureRecognizer.UP or UISwipeGestureRecognizer.RIGHT

        val recognizer9 = UIScreenEdgePanGestureRecognizer(this)
        recognizer9.tag = "screenEdges"
        recognizer9.actionListener = this

        //recognizer3.requireFailureOf(recognizer4);

        mDelegate!!.addGestureRecognizer(recognizer1)
        //        mDelegate.addGestureRecognizer(recognizer3);
        //        mDelegate.addGestureRecognizer(recognizer5);
        //        mDelegate.addGestureRecognizer(recognizer6);
        //        mDelegate.addGestureRecognizer(recognizer8);
        //        mDelegate.addGestureRecognizer(recognizer9);

        // start listening for MotionEvent

        mDelegate!!.startListeningView(mRoot)
    }

    override fun onContentChanged() {
        super.onContentChanged()

        mRoot = findViewById(R.id.activity_main)
    }

    override fun onGestureRecognized(recognizer: UIGestureRecognizer) {
        val dateTime = dateFormat!!.format(recognizer.lastEvent!!.eventTime)
        Log.d(
                javaClass.simpleName,
                "[" + dateTime + "] onGestureRecognized(" + recognizer + "). state: " + recognizer.state
        )
        (findViewById<View>(R.id.text) as TextView).text = recognizer.state!!.name
        (findViewById<View>(R.id.text2) as TextView).text = "[" + dateTime + "] " + recognizer.toString()
    }

    override fun shouldBegin(recognizer: UIGestureRecognizer): Boolean {
        return true
    }

    override fun shouldRecognizeSimultaneouslyWithGestureRecognizer(
            current: UIGestureRecognizer, recognizer: UIGestureRecognizer): Boolean {
        return true
    }

    override fun shouldReceiveTouch(recognizer: UIGestureRecognizer): Boolean {
        return true
    }
}