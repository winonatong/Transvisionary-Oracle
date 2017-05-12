package jp.org.example.geckour.glyph

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.preference.PreferenceManager
import android.util.Log
import android.view.*
import android.widget.RelativeLayout

import java.util.ArrayList
import java.util.Arrays

class FortuneActivity : Activity() {
    internal val version: Int = Build.VERSION.SDK_INT
    internal var offsetX: Float = 0f
    internal var offsetY: Float = 0f
    internal var scale: Float = 0f
    internal var sp: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tag = "onCreate"
        sp = PreferenceManager.getDefaultSharedPreferences(this)

        val actionBar = actionBar
        actionBar?.hide()
        setContentView(R.layout.activity_my)
    }

    internal var view: FortuneView? = null
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val tag = "onWindowFocusChanged"

        if (findViewById(R.id.root) != null) {
            val r = findViewById(R.id.root) as RelativeLayout
            offsetX = (r.width / 2).toFloat()
            offsetY = ((r.height / 2) - 100).toFloat()
            scale = offsetY * 2 / 1280
        }

        if (view == null) {
            view = FortuneView(this)
            setContentView(view)
        }
    }
  
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.my, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return super.onOptionsItemSelected(item)
    }

    internal inner class FortuneView(context: Context) : SurfaceView(context), SurfaceHolder.Callback, Runnable {

        val SHAPERS = arrayOf(
                arrayOf("ABANDON", "6,4,0,2,9,8"),
                arrayOf("ACCEPT", "8,2,1,8"),
                arrayOf("ADAPT", "10,2,0,1"),
                arrayOf("ADVANCE", "5,3,9"),
                arrayOf("AFTER", "6,4,0,1,7,6"),
                arrayOf("AGAIN", "9,3,2,0,4,1"),
                arrayOf("ALL", "5,6,7,8,9,10,5"),
                arrayOf("ANSWER", "3,4,1,0"),
                arrayOf("ASPIRATION", "10,3,5,4"),
                arrayOf("ATTACK", "9,3,5,4,7"),
                arrayOf("AVOID", "10,5,4,6,1"),
                arrayOf("BALANCE", "5,0,2,9,8,7,1,0"),
                arrayOf("BARRIER", "5,0,1,7"),
                arrayOf("BEFORE", "10,3,0,2,9,10"),
                arrayOf("BEGIN", "5,2,8,1"),
                arrayOf("BEING", "8,2,3,4,1,8"),
                arrayOf("BODY", "0,3,4,0"),
                arrayOf("BREATHE", "10,3,0,4,6"),
                arrayOf("CAPTURE", "6,1,0,2,9,8"),
                arrayOf("CHANGE", "2,0,8,1"),
                arrayOf("CHAOS", "9,10,5,6,4,0,2,8"),
                arrayOf("CHASE", "9,2,3,0,5"),
                arrayOf("CIVILIZATION", "10,3,2,1,4,6"),
                arrayOf("CLEAR", "5,0,8"),
                arrayOf("CLEAR ALL", "5,0,8,9,10,5,6,7,8"),
                arrayOf("CLOSE", "8,0,5,6,1,8"),
                arrayOf("COLLECTIVE", "9,2,3,5,4,1,7"),
                arrayOf("COMPLEX", "4,3,0,2"),
                arrayOf("CONFLICT", "9,3,2,1,4,7"),
                arrayOf("CONSEQUENCE", "10,3,2,1,7"),
                arrayOf("CONTEMPLATE", "5,6,7,8,2,3,0,4"),
                arrayOf("CONTRACT", "1,4,7"),
                arrayOf("COURAGE", "9,3,2,1"),
                arrayOf("CREATE", "9,2,0,4,6"),
                arrayOf("CREATION", "9,2,0,4,6"),
                arrayOf("CREATIVITY", "2,9,10,3,0,1,7,6,4"),
                //arrayOf("CREATIVITY", "3,8,0,3"),
                arrayOf("DANGER", "5,3,0,8"),
                arrayOf("DATA", "5,4,0,2,8"),
                arrayOf("DEFEND", "10,2,8,1,6"),
                arrayOf("DESTINATION", "6,7,8"),
                arrayOf("DESTINY", "3,0,4,1,2,8"),
                arrayOf("DESTROY", "10,3,0,1,7"),
                arrayOf("DESTRUCTION", "10,3,0,1,7"),
                arrayOf("DETERIORATE", "3,0,2,9"),
                arrayOf("DIE", "9,2,0,1,7"),
                arrayOf("DIFFICULT", "2,0,1,4,6"),
                arrayOf("DISCOVER", "6,7,8,9"),
                arrayOf("DISORDER", "9,10,5,6,4,0,2,8"),
                arrayOf("DISTANCE", "5,10,9"),
                arrayOf("DOORWAY", "10,3,4,6,7,1,2,9,10"),
                arrayOf("EASY", "4,0,2,8"),
                arrayOf("END", "8,0,5,6,1,8"),
                arrayOf("ENLIGHTENED", "8,7,6,5,3,0,4,3"),
                //arrayOf("ENLIGHTENED", "8,7,6,5,3,0,4,3,9"),
                arrayOf("ENLIGHTENMENT", "8,7,6,5,3,0,4,3"),
                //arrayOf("ENLIGHTENMENT", "8,7,6,5,3,0,4,3,9"),
                arrayOf("EQUAL", "2,3,4,1"),
                arrayOf("ERODE", "3,0,2,9"),
                arrayOf("ESCAPE", "5,6,4,3,2"),
                arrayOf("EVOLUTION", "5,0,3,2"),
                arrayOf("FAILURE", "5,0,4,1"),
                arrayOf("FEAR", "3,4,1,6"),
                arrayOf("FINALITY", "8,0,5,6,1,8"),
                arrayOf("FOLLOW", "5,4,6,7"),
                arrayOf("FORGET", "2,9"),
                arrayOf("FORWARD-TIME", "6,4,1,7"),
                arrayOf("FUTURE", "6,4,1,7"),
                arrayOf("GAIN", "10,2"),
                arrayOf("GROW", "9,3,2"),
                arrayOf("HARM", "7,1,0,3,5,4,0"),
                arrayOf("HARMONY", "0,1,8,2,0,4,5,3,0"),
                arrayOf("HAVE", "1,0,2,8"),
                arrayOf("HELP", "10,3,0,2,1"),
                arrayOf("HIDE", "3,4,6,1,2"),
                arrayOf("HUMAN", "8,2,3,4,1,8"),
                arrayOf("I", "8,3,4,8"),
                arrayOf("IDEA", "2,9,10,3,0,1,7,6,4"),
                //arrayOf("IDEA", "8,2,3,0,8"),
                arrayOf("IGNORE", "1,7"),
                arrayOf("IMPERFECT", "0,3,2,0,4,2"),
                arrayOf("IMPROVE", "6,4,0,1"),
                arrayOf("IMPURE", "8,0,3,2,0"),
                arrayOf("INDIVIDUAL", "9,8,7"),
                arrayOf("INTELLIGENCE", "9,2,3,0,4,6"),
                arrayOf("INTERRUPT", "5,0,3,10,9,2,0,8"),
                arrayOf("INSIDE", "3,4,1"),
                arrayOf("JOURNEY", "6,4,0,3,10,9,8"),
                arrayOf("KNOWLEDGE", "8,3,0,4,8"),
                arrayOf("LEAD", "5,10,9,2,8"),
                arrayOf("LEGACY", "9,2,3,10,5,6,4,1,7"),
                arrayOf("LESS", "3,0,4"),
                arrayOf("LIBERATE", "9,3,0,4,6,5"),
                arrayOf("LIE", "2,3,0,1,4,0"),
                arrayOf("LIFE FORCE", "8,0,4,1,8"),
                arrayOf("LIVE", "10,3,0,4,6"),
                arrayOf("LIVE AGAIN", "9,3,2,0,4,6"),
                arrayOf("LOSE", "6,1"),
                arrayOf("LOSS", "6,1"),
                arrayOf("ME", "8,3,4,8"),
                arrayOf("MESSAGE", "9,3,0,1,6"),
                //arrayOf("MESSAGE", "5,4,0,2,8"),
                arrayOf("MIND", "8,2,3,0,8"),
                arrayOf("MODIFY", "2,0,8,1"),
                arrayOf("MORE", "2,0,1"),
                arrayOf("MYSTERY", "10,3,5,4,3,2"),
                arrayOf("N'ZEER", "8,0,5,3,0,4,5"),
                arrayOf("NATURE", "9,2,3,4,1,7"),
                arrayOf("NEW", "4,1,7"),
                arrayOf("NO", "3,4,1"),
                arrayOf("NOT", "3,4,1"),
                arrayOf("NOURISH", "8,9,2,0,8"),
                arrayOf("NOW", "3,2,1,4"),
                arrayOf("OBSTACLE", "5,0,1,7"),
                arrayOf("OLD", "10,3,2"),
                arrayOf("OPEN", "8,2,1,8"),
                arrayOf("OPEN ALL", "8,2,1,8,9,10,5,6,7,8"),
                arrayOf("OPENING", "10,3,4,6,7,1,2,9,10"),
                arrayOf("OTHER", "5,2,1,5"),
                arrayOf("OUTSIDE", "5,10,9"),
                arrayOf("PAST", "10,3,2,9"),
                arrayOf("PATH", "5,0,2,9"),
                arrayOf("PEACE", "0,1,8,2,0,4,5,3,0"),
                arrayOf("PERFECTION", "5,0,2,9,8,7,1,0"),
                arrayOf("PERSPECTIVE", "9,2,0,4,5,3,0,1,7"),
                arrayOf("PORTAL", "10,3,4,6,7,1,2,9,10"),
                arrayOf("POTENTIAL", "5,0,1,7,6"),
                arrayOf("PRESENCE", "2,3,0,4,1,2,8,1"),
                arrayOf("PRESENT", "3,2,1,4"),
                arrayOf("PROGRESS", "5,0,3,2"),
                arrayOf("PURE", "5,0,4,1,0"),
                arrayOf("PURITY", "5,0,4,1,0"),
                arrayOf("PURSUE", "10,3,5,4"),
                //arrayOf("PURSUE", "9,2,3,0,5"),
                arrayOf("QUESTION", "5,4,3,2"),
                arrayOf("REACT", "4,3,0,1,7"),
                arrayOf("REBEL", "10,2,0,4,6,7"),
                arrayOf("RECHARGE", "0,3,10,5,0"),
                arrayOf("REDUCE", "1,4,7"),
                arrayOf("REINCARNATE", "9,3,2,0,4,6"),
                arrayOf("REPAIR", "0,3,10,5,0"),
                arrayOf("REPEAT", "9,3,2,0,4,1"),
                arrayOf("RESCUE", "2,0,1,6"),
                arrayOf("RESIST", "4,3,5,0,8,2"),
                arrayOf("RESISTANCE", "4,3,5,0,8,2"),
                arrayOf("RESTRAINT", "10,3,0,1,7,8"),
                arrayOf("RETREAT", "5,4,7"),
                arrayOf("SAFETY", "9,3,4,7"),
                arrayOf("SAN FRANCISCO", "5,10,3,0,6,4,0,7,1,0,9,2,0,8"),
                arrayOf("SAVE", "2,0,1,6"),
                arrayOf("SEARCH", "0,4,3,2,1"),
                arrayOf("SEE", "5,3"),
                arrayOf("SEEK", "0,4,3,2,1"),
                arrayOf("SELF", "9,8,7"),
                //arrayOf("SELF", "8,3,4,8"),
                arrayOf("SEPARATE", "10,3,2,0,4,1,7"),
                arrayOf("SHAPERS", "9,2,3,5,4,1,7"),
                arrayOf("SHARE", "7,1,2,9,8"),
                arrayOf("SHELL", "0,3,4,0"),
                arrayOf("SIGNAL", "5,4,0,2,8"),
                arrayOf("SIMPLE", "2,1"),
                arrayOf("SOUL", "8,0,4,1,8"),
                arrayOf("SPIRIT", "8,0,4,1,8"),
                arrayOf("STABILITY", "9,2,1,7"),
                arrayOf("STAY", "9,2,1,7"),
                arrayOf("STRONG", "2,3,4,1,2"),
                arrayOf("STRUGGLE", "4,3,5,0,8,2"),
                //arrayOf("STRUGGLE", "10,5,4,6,1"),
                arrayOf("SUCCESS", "5,0,3,2"),
                arrayOf("SUSTAIN", "5,0,4,6,7,1,0,8"),
                arrayOf("TECHNOLOGY", "6,4,0,2,3,0,1,7"),
                arrayOf("THEM", "5,2,1"),
                arrayOf("THOUGHT", "2,9,10,3,0,1,7,6,4"),
                //arrayOf("THOUGHT", "8,2,3,0,8"),
                arrayOf("TOGETHER", "9,2,0,4,3,0"),
                arrayOf("TRUTH", "3,0,1,4,0,2,3"),
                arrayOf("UNBOUNDED", "0,4,3,2,1,6,5,10,9,8,7"),
                arrayOf("[UNKNOWN]", "5,4,1,7,8,9,2,3,5"),
                arrayOf("[UNKNOWN]", "10,5,0,2,9,8,7,6"),
                arrayOf("[UNKNOWN]", "5,0,6,4,0,7,1,0,9,2,0,10,3,0,8"),
                arrayOf("US", "3,4,8"),
                arrayOf("USE", "0,1,6"),
                arrayOf("VICTORY", "8,3,5,4,8"),
                arrayOf("WANT", "9,2,8,1"),
                arrayOf("WAR", "9,3,5,4,7"),
                arrayOf("WE", "3,4,8"),
                arrayOf("WEAK", "10,3,4,1"),
                arrayOf("WORTH", "10,2,0,1,6"),
                arrayOf("XM", "2,3,4,1,0,2"),
                arrayOf("YOU", "5,2,1,5"),
                arrayOf("YOUR", "5,2,1,5")
        )

        var thread: Thread? = null
        var canvas: Canvas? = null
        val paint: Paint = Paint()
        var typeface: Typeface? = null
        var dbHelper: DBHelper
        var db: SQLiteDatabase
        val grainImg: Bitmap
        var scaledGrain: Bitmap? = null
        var dotTrue: Bitmap? = null
        var dotFalse: Bitmap? = null

        var isAttached: Boolean = false
        var cr = Math.PI / 3
        var radius: Double = 0.toDouble()
        var dotDiam: Int = 0
        var grainR: Float = 0f
        var isThrough = BooleanArray(11)
        var initTime: Long = 0
        var pressButtonTime: Long = 0
        var doVibrate = true
        var isPressedButton = false
        var resultId = ArrayList<Int>()
        var resultNames = ArrayList<String>()

        var dots = arrayOfNulls<PointF>(11)
        var Locus = ArrayList<Particle>()
        var locusPath = Path()
        var now: Long = 0
        var throughList: ThroughList = ThroughList()
        var holdTime: Long = 0
        var previousDot = -1

        var nextButtonPoint = arrayOfNulls<Point>(2)


        init {
            holder.addCallback(this)
            val tag = "FortuneView/init"
            dbHelper = DBHelper(context)
            db = dbHelper.readableDatabase

            radius = offsetX * 0.8
            dotDiam = (radius / 4.5).toInt()
            grainR = 20 * scale
            dotTrue = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.dot_t), dotDiam, dotDiam, false)
            dotFalse = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.dot_f), dotDiam, dotDiam, false)
            dots[0] = PointF(offsetX, offsetY * 1.2f)
            for (i in 1..4) {
                var j = i
                if (i > 1) {
                    j++
                    if (i > 3) {
                        j++
                    }
                }
                dots[i] = PointF((Math.cos(cr * (j - 0.5)) * (radius / 2) + offsetX).toFloat(), (Math.sin(cr * (j - 0.5)) * (radius / 2) + offsetY * 1.2).toFloat())
            }
            for (i in 5..10) {
                dots[i] = PointF((Math.cos(cr * (i - 0.5)) * radius + offsetX).toFloat(), (Math.sin(cr * (i - 0.5)) * radius + offsetY * 1.2).toFloat())
            }

            doVibrate = true
            for (i in 0..isThrough.lastIndex) {
                isThrough[i] = false
            }

            grainImg = BitmapFactory.decodeResource(resources, R.drawable.particle)

            now = System.currentTimeMillis()
            initTime = now
        }

        override fun surfaceCreated(holder: SurfaceHolder) {
            isAttached = true
            thread = Thread(this)
            thread?.start()
        }
        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
        override fun surfaceDestroyed(holder: SurfaceHolder) {
            val tag = "FortuneView/surfaceDestroyed"
            isAttached = false
        }

        override fun run() {
            val tag = "FortuneView/run"
            while (isAttached) {
                draw()
                try {
                    Thread.sleep(10)
                } catch (e: Exception) {
                    Log.e(tag, e.message)
                }
            }
        }

        fun draw() {
            val tag = "FortuneView/draw"
            var canvas: Canvas? = null
            try {
                canvas = holder.lockCanvas()
                this.canvas = canvas
                onDraw(canvas)
            } catch (e: IllegalStateException) {
                Log.e(tag, e.message)
            } finally {
                if (canvas != null) holder.unlockCanvasAndPost(canvas)
            }
        }

        public override fun onDraw(canvas: Canvas) {
            val tag = "FortuneView/onDraw"
            canvas.drawColor(if (version >= 23) resources.getColor(R.color.background, null) else resources.getColor(R.color.background))
            paint.isAntiAlias = true
            typeface = Typeface.createFromAsset(context.assets, "Coda-Regular.ttf")
            //paint.setTypeface(typeface)
            paint.typeface = typeface

            setGrainAlpha(releaseTime)

            for (i in 0..10) {
                if (isThrough[i]) {
                    canvas.drawBitmap(dotTrue, dots[i]!!.x - dotDiam / 2, dots[i]!!.y - dotDiam / 2, paint)
                } else {
                    canvas.drawBitmap(dotFalse, dots[i]!!.x - dotDiam / 2, dots[i]!!.y - dotDiam / 2, paint)
                }
            }
            synchronized(Locus) {
                for (particle in Locus) {
                    particle.move()
                }
            }
            if (isReleased) {
                drawResult(canvas)
            }

            now = if (isPressedButton) System.currentTimeMillis() - pressButtonTime + holdTime else System.currentTimeMillis()

            drawButton(canvas)
        }

        fun drawButton(canvas: Canvas) {
            val nextButtonWidth = 325 * scale
            val buttonHeight = 100 * scale
            val margin = 40 * scale
            val buttonBaseline = 35;
            nextButtonPoint[0] = Point((offsetX * 2 - nextButtonWidth - margin).toInt(), (offsetY * 2 - buttonHeight - margin).toInt())
            nextButtonPoint[1] = Point((offsetX * 2 - margin).toInt(), (offsetY * 2 - margin).toInt())

            paint.color = if (version >= 23) resources.getColor(R.color.button_text, null) else resources.getColor(R.color.button_text)
            paint.textAlign = Paint.Align.CENTER
            paint.textSize = 45 * scale
            paint.style = Paint.Style.FILL
            val dNext: Drawable

            dNext = if (version >= 23) resources.getDrawable(R.drawable.button1, null) else resources.getDrawable(R.drawable.button1)

            dNext.setBounds(nextButtonPoint[0]?.x ?: 0, nextButtonPoint[0]?.y ?: 0, nextButtonPoint[1]?.x ?: 0, nextButtonPoint[1]?.y ?: 0)
            dNext.draw(canvas)

            canvas.drawText("ASK SHAPERS", (nextButtonPoint[0]?.x ?: 0) + nextButtonWidth / 2, (nextButtonPoint[1]?.y ?: 0) - buttonBaseline * scale, paint)
        }

        internal inner class ThroughList {
            var dots: ArrayList<Int>

            constructor() {
                dots = ArrayList<Int>()
            }

            constructor(argDots: ArrayList<Int>) {
                dots = ArrayList(argDots)
            }

            constructor(argDots: Array<String>) {
                val tag = "ThroughList"
                dots = ArrayList<Int>()
                for (s in argDots) {
                    try {
                        dots.add(Integer.parseInt(s))
                    } catch (e: Exception) {
                        Log.e(tag, e.message)
                    }

                }
            }
        }

        fun setGrainAlpha(time: Long) {
            scaledGrain = Bitmap.createScaledBitmap(grainImg, (grainR * 2).toInt(), (grainR * 2).toInt(), false)
            val w = scaledGrain?.width ?: 0
            val h = scaledGrain?.height ?: 0

            val pixels = IntArray(w * h)
            scaledGrain?.getPixels(pixels, 0, w, 0, 0, w, h)

            var subAlpha = 0
            if (time > -1) {
                subAlpha = calcSubAlpha(time)
            }
            if (subAlpha != 0) {
                for (y in 0..h - 1) {
                    for (x in 0..w - 1) {
                        var a = pixels[x + y * w]
                        var b = a
                        a = a.ushr(24)

                        if (a != 0) {
                            a -= subAlpha
                            if (a < 0) {
                                a = 0
                            }
                        }
                        a = a shl 24

                        b = b and 16777215

                        pixels[x + y * w] = a + b
                    }
                }
                scaledGrain?.setPixels(pixels, 0, w, 0, 0, w, h)
            }
        }

        private fun calcSubAlpha(time: Long): Int {
            val tol = 500
            if (now - time > tol) {
                resetThrough()
                var result = ((now - time - tol.toLong()) / 2f).toInt()
                if (result > 255) {
                    result = 255
                }
                return result
            } else {
                return 0
            }
        }

        internal inner class Particle(var x0: Float, var y0: Float, val canvas: Canvas) {
            var grain = ArrayList<Grain>()
            var phase = 0
            var moveFrames: Long = 300
            var initFrame: Long = 0
            var v = 0.15

            init {
                initFrame = System.currentTimeMillis()
                grain.add(Grain(x0, y0, true, null))
                grain.add(Grain(x0, y0, true, null))
                grain.add(Grain(x0, y0, true, null))
                grain.add(Grain(grain[0].origin.x, grain[0].origin.y, false, grain[0].step0))
                grain.add(Grain(grain[0].origin.x, grain[0].origin.y, false, grain[0].step0))
                grain.add(Grain(grain[0].origin.x, grain[0].origin.y, false, grain[0].step0))
                grain.add(Grain(grain[1].origin.x, grain[1].origin.y, false, grain[1].step0))
                grain.add(Grain(grain[1].origin.x, grain[1].origin.y, false, grain[1].step0))
                grain.add(Grain(grain[1].origin.x, grain[1].origin.y, false, grain[1].step0))
                grain.add(Grain(grain[2].origin.x, grain[2].origin.y, false, grain[2].step0))
                grain.add(Grain(grain[2].origin.x, grain[2].origin.y, false, grain[2].step0))
                grain.add(Grain(grain[2].origin.x, grain[2].origin.y, false, grain[2].step0))
            }

            fun move() {
                val diffFrames = System.currentTimeMillis() - initFrame
                if (diffFrames > moveFrames || isReleased) phase = 1

                when (phase) {
                    0 -> {
                        val param = (moveFrames - diffFrames) / (moveFrames.toFloat())
                        for (i in grain.indices) {
                            grain[i].x = grain[i].step1.x + grain[i].diff.x * param
                            grain[i].y = grain[i].step1.y + grain[i].diff.y * param
                        }
                    }
                    1 -> {
                        for (i in grain.lastIndex downTo 0) {
                            if (!grain[i].isOrigin) {
                                grain.removeAt(i)
                            }
                        }
                        for (i in grain.indices) {
                            val param = Math.cos(grain[i].a0)
                            grain[i].x += (Math.cos(grain[i].a1) * grain[i].circleR * param).toFloat()
                            grain[i].y += (Math.sin(grain[i].a1) * grain[i].circleR * param).toFloat()
                            grain[i].a0 += v
                        }
                    }
                }
                draw()
            }

            internal inner class Grain(x: Float, y: Float, isOrigin: Boolean, start: PointF?) {
                var x: Float
                var y: Float
                val isOrigin = isOrigin
                var origin = PointF()
                var step0 = PointF()
                var step1 = PointF()
                var diff = PointF()
                val pi2 = Math.PI * 2.0
                var a0 = Math.random() * pi2
                val a1 = Math.random() * pi2
                var circleR = Math.random() * 0.5 + 0.7

                init {
                    //タッチした点
                    origin.x = x
                    origin.y = y

                    val margin = Math.random() * offsetX * 0.05

                    //収束への出発点
                    var blurR: Double
                    var blurA: Double
                    blurA = Math.random() * pi2
                    if (isOrigin) {
                        blurR = offsetX * 0.4 * Math.random() + margin
                        step0.x = origin.x + (blurR * Math.cos(blurA)).toFloat()
                        step0.y = origin.y + (blurR * Math.sin(blurA)).toFloat()
                    } else if (start != null) {
                        blurR = offsetX * 0.2 * Math.random()
                        step0.x = start.x + (blurR * Math.cos(blurA)).toFloat()
                        step0.y = start.y + (blurR * Math.sin(blurA)).toFloat()
                    }

                    //収束点
                    if (isOrigin) {
                        blurR = margin
                        blurA = Math.random() * pi2
                        step1.x = origin.x + (blurR * Math.cos(blurA)).toFloat()
                        step1.y = origin.y + (blurR * Math.sin(blurA)).toFloat()
                    } else  {
                        step1.x = x
                        step1.y = y
                    }

                    //収束までの距離
                    diff.x = step0.x - step1.x
                    diff.y = step0.y - step1.y

                    if (isReleased) {
                        this.x = step1.x
                        this.y = step1.y
                    } else {
                        this.x = step0.x
                        this.y = step0.y
                    }
                }
            }

            private fun draw() {
                //paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.ADD))
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.ADD)
                for (gr in grain) {
                    canvas.drawBitmap(scaledGrain, gr.x - grainR, gr.y - grainR, paint)
                }
                //paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_OVER))
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
            }
        }



        fun putParticles(throughList: ThroughList, canvas: Canvas) {
            val tag = "FortuneView/putParticles"
            val interval = 25 * scale
            val length = FloatArray(throughList.dots.lastIndex)
            for (i in 1..throughList.dots.lastIndex) {
                val dotI0 = dots[throughList.dots[i]]
                val dotI1 = dots[throughList.dots[i - 1]]
                if (dotI0 != null && dotI1 != null) {
                    length[i - 1] = Math.sqrt(Math.pow((dotI1.x - dotI0.x).toDouble(), 2.0) + Math.pow((dotI1.y - dotI0.y).toDouble(), 2.0)).toFloat()
                }
            }
            synchronized(Locus) {
                Locus.clear()
            }
            for (i in length.indices) {
                val dotI0 = dots[throughList.dots[i]]
                val dotI1 = dots[throughList.dots[i + 1]]
                if (dotI0 != null && dotI1 != null) {
                    val unitV = floatArrayOf((dotI1.x - dotI0.x) / length[i], (dotI1.y - dotI0.y) / length[i])
                    var x = dotI0.x
                    var y = dotI0.y

                    val sumLength = floatArrayOf(0f, 0f)
                    synchronized(Locus) {
                        val absX = Math.abs(dotI1.x - dotI0.x)
                        val absY = Math.abs(dotI1.y - dotI0.y)
                        val dX = unitV[0] * interval
                        val dY = unitV[1] * interval
                        val dXa = absX * interval / length[i]
                        val dYa = absY * interval / length[i]
                        while (sumLength[0] <= absX && sumLength[1] <= absY) {
                            Locus.add(Particle(x, y, canvas))
                            x += dX
                            y += dY
                            sumLength[0] += dXa
                            sumLength[1] += dYa
                        }
                    }
                }
            }
        }

        fun searchIdFromList(): ArrayList<String> {
            val tag = "FortuneActivity";

            var glyphName = ArrayList<String>()

            for(shaper in SHAPERS) {
                Log.v(tag, shaper[0] + shaper[1])

                val dotsSplit = shaper[1].split(",")
                val throughListInRow = ThroughList(dotsSplit.toTypedArray())

                if (judgeLocus(throughListInRow, throughList)) {
                    glyphName.add(shaper[0])
                }
            }

            return glyphName
        }

        fun drawResult(canvas: Canvas) {
            val tag = "drawResult"

            var resultStr = resultNames

            paint.color = Color.WHITE
            paint.style = Paint.Style.FILL
            paint.strokeWidth = 1f
            paint.textSize = 70 * scale
            paint.textAlign = Paint.Align.CENTER
            for (i in 0..resultStr.lastIndex) canvas.drawText(resultStr[i], offsetX, (offsetY * 1.1f - offsetX) / 2f + i * paint.textSize * 1.2f + 100f, paint)
        }


        fun normalizePaths(paths: ArrayList<IntArray>): ArrayList<IntArray> {
            val returnPaths = ArrayList<IntArray>()
            for (i in paths.indices) {
                var match = 0
                val srcPath = paths[i]
                for (j in i + 1..paths.size - 1) {
                    val destPath = paths[j]
                    val tempPath = intArrayOf(destPath[1], destPath[0])
                    if (Arrays.equals(srcPath, destPath) || Arrays.equals(srcPath, tempPath)) {
                        match++
                    }
                }
                if (match == 0) {
                    returnPaths.add(srcPath)
                }
            }

            return returnPaths
        }

        fun judgeLocus(answer: ThroughList, through: ThroughList): Boolean {
            val tag = "FortuneView/judgeLocus"
            val answerPaths = ArrayList<IntArray>()
            var passedPaths = ArrayList<IntArray>()

            for (i in 0..answer.dots.size - 1 - 1) {
                val path = intArrayOf(answer.dots[i], answer.dots[i + 1])
                answerPaths.add(path)
            }
            for (i in 0..through.dots.size - 1 - 1) {
                val path = intArrayOf(through.dots[i], through.dots[i + 1])
                passedPaths.add(path)
            }
            passedPaths = normalizePaths(passedPaths)

            if (answerPaths.size == passedPaths.size) {
                val clearFrags = BooleanArray(answerPaths.size)
                for (i in answerPaths.indices) {
                    for (path in passedPaths) {
                        val tempPaths = intArrayOf(path[1], path[0])
                        if (Arrays.equals(answerPaths[i], path) || Arrays.equals(answerPaths[i], tempPaths)) {
                            clearFrags[i] = true
                        }
                    }
                }
                var clearC = 0
                for (flag in clearFrags) {
                    if (flag) {
                        clearC++
                    }
                }
                return (clearC == answerPaths.size)
            } else {
                return false
            }
        }

        fun setLocusStart(x: Float, y: Float, doCD: Boolean, canvas: Canvas) {
            synchronized(Locus) {
                Locus.add(Particle(x, y, canvas))

                if (doCD) {
                    setCollision(x, y, x, y)
                }
                locusPath.moveTo(x, y)
            }
        }

        fun setLocus(x: Float, y: Float, doCD: Boolean, canvas: Canvas) {
            synchronized(Locus) {
                Locus.add(Particle(x, y, canvas))

                if (doCD) {
                    setCollision(x, y, Locus[Locus.size - 2].x0, Locus[Locus.size - 2].y0)
                }
                locusPath.lineTo(x, y)
            }
        }

        fun setCollision(x0: Float, y0: Float, x1: Float, y1: Float) {
            var collisionDot = -1
            val tol = 35 * scale
            for (i in 0..10) {
                if (x0 == x1 && y0 == y1) {
                    //円の方程式にて当たり判定
                    val difX = x0 - dots[i]!!.x
                    val difY = y0 - dots[i]!!.y
                    val r = offsetX * 0.8 / 18 + tol
                    if (difX * difX + difY * difY < r * r) {
                        isThrough[i] = true
                        collisionDot = i
                    }
                } else {
                    //線分と円の当たり判定
                    val a = y0 - y1
                    val b = x1 - x0
                    val c = x0 * y1 - x1 * y0
                    val d = (a * dots[i]!!.x + b * dots[i]!!.y + c) / Math.sqrt((a * a + b * b).toDouble())
                    val lim = offsetX * 0.8 / 18 + tol
                    if (-lim <= d && d <= lim) {
                        //線分への垂線と半径
                        val difX0 = dots[i]!!.x - x0
                        val difX1 = dots[i]!!.x - x1
                        val difY0 = dots[i]!!.y - y0
                        val difY1 = dots[i]!!.y - y1
                        val difX10 = x1 - x0
                        val difY10 = y1 - y0
                        val inner0 = (difX0 * difX10 + difY0 * difY10).toDouble()
                        val inner1 = (difX1 * difX10 + difY1 * difY10).toDouble()
                        val d0 = Math.sqrt((difX0 * difX0 + difY0 * difY0).toDouble())
                        val d1 = Math.sqrt((difX1 * difX1 + difY1 * difY1).toDouble())
                        if (inner0 * inner1 <= 0) {
                            //内積
                            isThrough[i] = true
                            collisionDot = i
                        } else if (d0 < lim || d1 < lim) {
                            isThrough[i] = true
                            collisionDot = i
                        }
                    }
                }
            }
            if (collisionDot != -1 && (throughList.dots.size < 1 || throughList.dots[throughList.dots.size - 1] !== collisionDot)) {
                throughList.dots.add(collisionDot)
                if (doVibrate) {
                    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    vibrator.vibrate(30)
                }
                previousDot = collisionDot
            }
        }

        fun resetLocus() {
            locusPath.reset()
            synchronized(Locus) {
                Locus.clear()
            }
        }

        fun resetThrough() {
            for (i in 0..10) {
                isThrough[i] = false
            }
        }

        var downX = 0f
        var downY = 0f
        var memX = 0f
        var memY = 0f
        var isReleased = false
        var releaseTime: Long = -1
        var isOnNext = Array(3, {i -> false})

        override fun onTouchEvent(event: MotionEvent): Boolean {
            val tag = "onTouchEvent"

            val lim = 15 * scale
            when (event.action) {
                MotionEvent.ACTION_DOWN //タッチ
                -> {
                    isOnNext[0] = nextButtonPoint[0]?.x ?: -1 <= downX && downX <= nextButtonPoint[1]?.x ?: -1 && nextButtonPoint[0]?.y ?: -1 <= downY && downY <= nextButtonPoint[1]?.y ?: -1
                    if(isOnNext[0]) {
                        startActivity(Intent(this@FortuneActivity, MyActivity::class.java))
                    }

                    downX = event.x
                    downY = event.y
                    if (isReleased) {
                        isReleased = false
                        resetLocus()
                        resetThrough()
                        throughList.dots.clear()
                        resultNames.clear()
                    }
                    setLocusStart(downX, downY, true, canvas!!)
                    memX = downX
                    memY = downY
                }
                MotionEvent.ACTION_MOVE //スワイプ
                -> {
                    val currentX = event.x
                    val currentY = event.y
                    if (currentX + lim < memX || memX + lim < currentX || currentY + lim < memY || memY + lim < currentY) {
                        if (Locus.size == 0) {
                            setLocusStart(currentX, currentY, true, canvas!!)
                        } else {
                            setLocus(currentX, currentY, true, canvas!!)
                        }
                        memX = currentX
                        memY = currentY
                    }
                }
                MotionEvent.ACTION_UP //リリース
                -> {
                    isReleased = true
                    var list = ""
                    for (throughDot in throughList.dots) {
                        list += "$throughDot,"
                    }
                    Log.v(tag, "throughList:$list")
                    resetLocus()
                    if (throughList.dots.size > 0) {
                        putParticles(throughList, canvas!!)
                    }
                    synchronized(resultNames) {
                        resultNames = searchIdFromList()
                    }
                }
                MotionEvent.ACTION_CANCEL
                -> {}
            }
            return true
        }
    }
}