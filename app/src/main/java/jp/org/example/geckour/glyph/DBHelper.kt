package jp.org.example.geckour.glyph

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteStatement
import android.util.Log

class DBHelper(context: Context) : SQLiteOpenHelper(context, DBHelper.DB_NAME, null, DBHelper.DB_VERSION) {
    companion object {
        val DB_NAME = "shaper.db"
        val TABLE_NAME1 = "shapers"
        val TABLE_NAME2 = "sets"
        val DB_VERSION = 16
    }

    public val SHAPERS = arrayOf(
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

    public val SETS = arrayOf(
            arrayOf("5", "YOUR,POTENTIAL,DESTROY,US,ALL", null),
            arrayOf("5", "XM,SEARCH,DISCOVER,NEW,IDEA", null),
            arrayOf("5", "XM ,HELP,OPEN,PORTAL,POTENTIAL", null),
            arrayOf("5", "XM ,DATA,OPEN,ALL,TECHNOLOGY", null),
            arrayOf("5", "WEAK,MIND,SOUL,CHAOS,STRONG", null),
            arrayOf("5", "WE,SHARE,SIMPLE,TRUTH,TOGETHER", null),
            arrayOf("5", "UNBOUNDED,PORTAL,POTENTIAL,ESCAPE,ALL", null),
            arrayOf("5", "UNBOUNDED,HUMAN,POTENTIAL,CREATE,SAFETY", null),
            arrayOf("5", "UNBOUNDED,DATA,NOURISH,FUTURE,TECHNOLOGY", null),
            arrayOf("5", "TOGETHER,NOW,US,CAPTURE,HARMONY", null),
            arrayOf("5", "TECHNOLOGY,HELP,HUMAN,BODY,SOUL", null),
            arrayOf("5", "STRONG,SOUL,HELP,OUTSIDE,HARMONY", null),
            arrayOf("5", "STRONG,NATURE,TOGETHER,PURE,FUTURE", null),
            arrayOf("5", "STRONG,MIND,STRONG,BODY,SOUL", null),
            arrayOf("5", "STRONG,MIND,PEACE,SAFETY,INSIDE", null),
            arrayOf("5", "STRONG,MIND,NOW,STRONG,FUTURE", null),
            arrayOf("5", "STRONG,MIND,LEAD,ENLIGHTENMENT,TOGETHER", null),
            arrayOf("5", "STRONG,IDEA,CREATE,NEW,TECHNOLOGY", null),
            arrayOf("5", "SEARCH,YOUR,MIND,YOUR,SOUL", null),
            arrayOf("5", "SEARCH,YOUR,MIND,PURE,IDEA", null),
            arrayOf("5", "SEARCH,SHAPERS,MESSAGE,QUESTION,FUTURE", null),
            arrayOf("5", "SEARCH,NEW,PERSPECTIVE,CONTEMPLATE,TRUTH", null),
            arrayOf("5", "SEARCH,MESSAGE,CAPTURE,NEW,PERSPECTIVE", null),
            arrayOf("5", "SEARCH,CHAOS,DAMAGE,YOUR,SOUL", null),
            arrayOf("5", "SEARCH,CHAOS,DAMAGE,YOUR,MIND", null),
            arrayOf("5", "SEARCH ,ENLIGHTENMENT,CAPTURE,HUMAN,VICTORY", null),
            arrayOf("5", "SAVE,YOUR,SOUL,CLEARALL,CHAOS", null),
            arrayOf("5", "SAVE,XM,DATA,SAVE,MESSAGE", null),
            arrayOf("5", "SAVE,NATURE,NOW,FUTURE,SAFETY", null),
            arrayOf("5", "RESISTANCE,TECHNOLOGY,DESTROY,US,ALL", null),
            arrayOf("5", "RESISTANCE,NOW,DESTROY,YOUR,SOUL", null),
            arrayOf("5", "RESISTANCE,MESSAGE,HARM,ALL,CIVILIZATION", null),
            arrayOf("5", "RESISTANCE,HARM,BODY,ENLIGHTENMENT,SAVE", null),
            arrayOf("5", "QUESTION,YOUR,IMPURE,HUMAN,MIND", null),
            arrayOf("5", "QUESTION,XM,MESSAGE,QUESTION,MIND", null),
            arrayOf("5", "QUESTION,NOT,YOU,CREATE,DESTINY", null),
            arrayOf("5", "QUESTION,IDEA,CREATE,NEW,PRESENT", null),
            arrayOf("5", "PURSUE,YOUR,TRUTH,PATH,NOW", null),
            arrayOf("5", "PURSUE,XM,CREATE,NEW,FUTURE", null),
            arrayOf("5", "PURSUE,TRUTH,GAIN,PURE,CIVILIZATION", null),
            arrayOf("5", "PURSUE,TRUTH,CREATE,PURE,CIVILIZATION", null),
            arrayOf("5", "PURSUE,RESISTANCE,HARM,US,ALL", null),
            arrayOf("5", "PURSUE,PURE,MIND,CAPTURE,VICTORY", null),
            arrayOf("5", "PURSUE ,IMPURE,NATURE,HARM,SOUL", null),
            arrayOf("5", "PURE,PRESENT,CAPTURE,STRONG,FUTURE", null),
            arrayOf("5", "PAST,DATA,CREATE,FUTURE,MESSAGE", null),
            arrayOf("5", "OPENALL,POTENTIAL,CAPTURE,HUMAN,NATURE", null),
            arrayOf("5", "OPENALL,HUMAN,TECHNOLOGY,POTENTIAL,NOW", null),
            arrayOf("5", "OPEN,PORTAL,END,ALL,JOURNEY", null),
            arrayOf("5", "NOURISH,YOUR,BODY,NOURISH,CIVILIZATION", null),
            arrayOf("5", "NOURISH,RESISTANCE,HARM,ENLIGHTENMENT,CIVILIZATION", null),
            arrayOf("5", "NEW,IDEA,SEARCH,XM,QUESTION", null),
            arrayOf("5", "NATURE,TECHNOLOGY,TOGETHER,SAVE,HUMAN", null),
            arrayOf("5", "MYSTERY,REDUCE,NOW,SEE,TRUTH", null),
            arrayOf("5", "MORE,PURE,DATA,LESS,QUESTION", null),
            arrayOf("5", "LEAD,RESISTANCE,DESTROY,YOUR,SOUL", null),
            arrayOf("5", "LEAD,HUMAN,OUTSIDE,HELP,NATURE", null),
            arrayOf("5", "JOURNEY,PURE,PATH,PURSUE,PEACE", null),
            arrayOf("5", "IMPURE,TECHNOLOGY,HARM,PURE,NATURE", null),
            arrayOf("5", "IMPURE,TECHNOLOGY,DESTROY,HUMAN,NATURE", null),
            arrayOf("5", "IMPURE,SOUL,HARM,YOUR,BODY", null),
            arrayOf("5", "IMPURE,PAST,DESTROY,YOUR,PRESENT", null),
            arrayOf("5", "IMPURE,PAST,CREATE,STRUGGLE,NOW", null),
            arrayOf("5", "IMPURE,PAST,DESTROY,YOUR,FUTURE", null),
            arrayOf("5", "IMPURE,MIND,DAMAGE,YOUR,SOUL", null),
            arrayOf("5", "IMPURE,IDEA,CREATE,FUTURE,CHAOS", null),
            arrayOf("5", "IMPURE,SOUL,DAMAGE,US,HUMAN", null),
            arrayOf("5", "HURT,YOUR,BODY,HURT,MIND", null),
            arrayOf("5", "HUMAN,TOGETHER,AVOID,FUTURE,HARM", null),
            arrayOf("5", "HUMAN,NATURE,PURSUE,PURE,CIVILIZATION", null),
            arrayOf("5", "HUMAN,NATURE,HAVE,STRONG,POTENTIAL", null),
            arrayOf("5", "HUMAN,LIE,HARM,US,ALL", null),
            arrayOf("5", "HUMAN,FUTURE,ANSWER,ALL,QUESTION", null),
            arrayOf("5", "HUMAN,COURAGE,SAVE,YOUR,SOUL", null),
            arrayOf("5", "HUMAN,CIVILIZATION,DESTROY,ALL,NATURE", null),
            arrayOf("5", "HIDE,YOUR,IMPURE,HUMAN,SOUL", null),
            arrayOf("5", "HELP,YOUR,CIVILIZATION,CAPTURE,TRUTH", null),
            arrayOf("5", "HELP,US,HUMAN,SAVE,NATURE", null),
            arrayOf("5", "HELP,US,HELP,YOUR,SOUL", null),
            arrayOf("5", "HELP,US,HELP,YOUR,MIND", null),
            arrayOf("5", "HELP,RESISTANCE,HARM,US,ALL", null),
            arrayOf("5", "HELP,NATURE,SAVE,YOUR,SOUL", null),
            arrayOf("5", "HELP,HUMAN,SAVE,SOUL,TOGETHER", null),
            arrayOf("5", "HELP,HUMAN,JOURNEY,SEARCH,MIND", null),
            arrayOf("5", "HELP,ALL,DATA,HELP,MESSAGE", null),
            arrayOf("5", "HAVE,NEW,DATA,BEGIN,JOURNEY", null),
            arrayOf("5", "HARM,NATURE,HARM,US,ALL", null),
            arrayOf("5", "HARM,BODY,HARM,YOUR,MIND", null),
            arrayOf("5", "FUTURE,HUMAN,DESTROY,ALL,NATURE", null),
            arrayOf("5", "ESCAPE,CHAOS,GAIN,PURE,MIND", null),
            arrayOf("5", "ENLIGHTENMENT,JOURNEY,HELP,US,ALL", null),
            arrayOf("5", "DESTROY,YOUR,WEAK,HUMAN,NATURE", null),
            arrayOf("5", "DESTROY,NATURE,NOW,DESTROY,FUTURE", null),
            arrayOf("5", "DESTROY,FEAR,CAPTURE,HUMAN,POTENTIAL", null),
            arrayOf("5", "DESTROY,ENLIGHTENMENT,DESTROY,YOUR,SOUL", null),
            arrayOf("5", "DESTROY,BARRIER,CREATE,NEW,PATH", null),
            arrayOf("5", "DEFEND,YOUR,MIND,CAPTURE,HARMONY", null),
            arrayOf("5", "DAMAGE,NATURE,NOW,DESTROY,FUTURE", null),
            arrayOf("5", "ADVANCE,YOUR,PURE,HUMAN,JOURNEY", null),
            arrayOf("5", "CLEAR,YOUR,CHAOS,MIND,NOW", null),
            arrayOf("5", "CLEAR,IMPURE,MIND,OPEN,SOUL", null),
            arrayOf("5", "CLEAR,IMPURE,IDEA,CAPTURE,HARMONY", null),
            arrayOf("5", "CHAOS,MIND,HARM,YOUR,SOUL", null),
            arrayOf("5", "CHANGE,PERSPECTIVE,LEAD,ENLIGHTENMENT,NOW", null),
            arrayOf("5", "CHANGE,NOW,AVOID,FUTURE,STRUGGLE", null),
            arrayOf("5", "CHANGE,HUMAN,NATURE,DESTROY,SOUL", null),
            arrayOf("5", "CHANGE,HUMAN,BODY,HARM,SOUL", null),
            arrayOf("5", "CHANGE,BODY,NOW,CHANGE,MIND", null),
            arrayOf("5", "CAPTURE,YOUR,WEAK,HUMAN,NATURE", null),
            arrayOf("5", "CAPTURE,UNBOUNDED,PORTAL,POTENTIAL,NOW", null),
            arrayOf("5", "CAPTURE,PURE,IDEA,HELP,MIND", null),
            arrayOf("5", "CAPTURE,POTENTIAL,FUTURE,VICTORY,NOW", null),
            arrayOf("5", "CAPTURE,PEACE,LEAD,HUMAN,LEGACY", null),
            arrayOf("5", "CAPTURE,NATURE,PERSPECTIVE,HELP,SOUL", null),
            arrayOf("5", "BEGIN,NEW,XM,FUTURE,POTENTIAL", null),
            arrayOf("5", "AVOID,PURE,PATH,HARM,MIND", null),
            arrayOf("5", "AVOID,ENLIGHTENMENT,DISCOVER,ALL,CHAOS", null),
            arrayOf("5", "AVOID,CHAOS,HARMONY,YOUR,MIND", null),
            arrayOf("5", "ATTACK,NATURE,DESTROY,US,ALL", null),
            arrayOf("5", "ATTACK,HUMAN,ATTACK,YOUR,SOUL", null),
            arrayOf("5", "ATTACK,ALL,HUMAN,ENLIGHTENMENT,NOW", null),
            arrayOf("5", "ATTACK,ALL,ENLIGHTENMENT,HUMAN,NOW", null),
            arrayOf("5", "ATTACK ,YOUR,BODY,HARM,MIND", null),
            arrayOf("5", "AGAIN,HUMAN,DESTROY,FUTURE,NATURE", null),
            arrayOf("5", "ABANDON,YOUR,HUMAN,NATURE,NOW", null),
            arrayOf("5", "ABANDON,YOUR,CHAOS,GAIN,HARMONY", null),
            arrayOf("5", "ABANDON,TRUTH,PURSUE,MIND,LIE", null),
            arrayOf("5", "ABANDON,PURE,MIND,DESTROY,LEGACY", null),
            arrayOf("5", "ABANDON,PAST,STRONG,INSIDE,OUTSIDE", null),
            arrayOf("5", "ABANDON,IMPURE,PAST,STRONG,FUTURE", null),
            arrayOf("5", "ABANDON,IMPURE,PAST,GAIN,FUTURE", null),
            arrayOf("5", "ABANDON,HUMAN,NATURE,HARM,ALL", null),
            arrayOf("5", "ABANDON,HUMAN,FEAR,CAPTURE,HARMONY", null),
            arrayOf("5", "ABANDON,ENLIGHTENMENT,CAPTURE,ALL,CHAOS", null),
            arrayOf("4", "YOUR,PATH,LEAD,KNOWLEDGE", null),
            arrayOf("4", "YOU,ANSWER,USE,INTELLIGENCE", null),
            arrayOf("4", "STRONG,NOW,SAFETY,FUTURE", null),
            arrayOf("4", "SHARE,ENLIGHTENMENT,PATH,SUCCESS", null),
            arrayOf("4", "SEEK,ANSWER,INSIDE,MIND", null),
            arrayOf("4", "SEEK ,ANSWER,INSIDE,SELF", null),
            arrayOf("4", "SEEK ,ANSWER,INSIDE,ENLIGHTENMENT", null),
            arrayOf("4", "SEEK ,ANSWER,INSIDE,CHAOS", null),
            arrayOf("4", "SEARCH,RESISTANCE,DISCOVER,HARM", null),
            arrayOf("4", "SEARCH,RESISTANCE,DISCOVER,CHAOS", null),
            arrayOf("4", "SEARCH,PURE,NATURE,PATH", null),
            arrayOf("4", "SEARCH,COURAGE,CAPTURE,VICTORY", null),
            arrayOf("4", "SEARCH,COURAGE,ABANDON,FEAR", null),
            arrayOf("4", "RESISTANCE,HARM,YOUR,BODY", null),
            arrayOf("4", "RESISTANCE,END,ALL,CIVILIZATION", null),
            arrayOf("4", "RESISTANCE,DESTROY,YOUR,MIND", null),
            arrayOf("4", "OPEN,MIND,ENLIGHTENED,SELF", null),
            arrayOf("4", "MORE,RESISTANCE,LESS,HARMONY", null),
            arrayOf("4", "MORE,NATURE,LESS,TECHNOLOGY", null),
            arrayOf("4", "LOSE ,PAST,LOSE ,FUTURE", null),
            arrayOf("4", "LESS,TECHNOLOGY,MORE,NATURE", null),
            arrayOf("4", "LESS,ENLIGHTENMENT,MORE,HARM", null),
            arrayOf("4", "LEAD,PURE,HUMAN,POTENTIAL", null),
            arrayOf("4", "INSIDE,MIND,OUTSIDE,BODY", null),
            arrayOf("4", "IMPROVE,QUESTION,IMPROVE,ANSWER", null),
            arrayOf("4", "IMPERFECT,MESSAGE,BEGIN,AGAIN", null),
            arrayOf("4", "HUMAN,NATURE,IMPURE,CHAOS", null),
            arrayOf("4", "HAVE,ANSWER,SEE,TRUTH", null),
            arrayOf("4", "HARM,US,ALL,NOW", null),
            arrayOf("4", "GAIN,STRONG,HUMAN,EVOLUTION", null),
            arrayOf("4", "ENLIGHTENED,HAVE,ALL,ANSWER", null),
            arrayOf("4", "END,XM,TECHNOLOGY,POTENTIAL", null),
            arrayOf("4", "END,DATA,BEGIN,MESSAGE", null),
            arrayOf("4", "DISCOVER,PURE,HUMAN,NATURE", null),
            arrayOf("4", "DEFEND,YOUR,FUTURE,LEGACY", null),
            arrayOf("4", "DEFEND,PURE,HUMAN,TRUTH", null),
            arrayOf("4", "ALL,QUESTION,CHANGE,ANSWER", null),
            arrayOf("4", "ALL,HUMAN,DESTROY,NATURE", null),
            arrayOf("3", "YOUR,LEGACY,TRUTH", null),
            arrayOf("3", "TECHNOLOGY,SAVE,YOU", null),
            arrayOf("3", "SEEK,EASY,PATH", null),
            arrayOf("3", "SEEK,ANSWER,FAILURE", null),
            arrayOf("3", "SEEK,ANSWER,DIFFICULT", null),
            arrayOf("3", "SEEK,ANSWER,COMPLEX", null),
            arrayOf("3", "SEE,EASY,PEACE", null),
            arrayOf("3", "SEE,EASY,FUTURE", null),
            arrayOf("3", "OPEN,XM,POTENTIAL", null),
            arrayOf("3", "LOSE,MIND,AGAIN", null),
            arrayOf("3", "HUMAN,QUESTION,WEAK", null),
            arrayOf("3", "HAVE,EASY,SUCCESS", null),
            arrayOf("3", "FUTURE,LOSE,SUCCESS", null),
            arrayOf("3", "ESCAPE,COMPLEX,ANSWER", null),
            arrayOf("3", "DANGER,CONFLICT,DEATH", null),
            arrayOf("3", "CONTEMPLATE,QUESTION,MORE", null),
            arrayOf("3", "CONTEMPLATE,QUESTION,CONSEQUENCE", null),
            arrayOf("3", "CONTEMPLATE,ANSWER,MORE", null),
            arrayOf("3", "CHANGE,MIND,NOW", null),
            arrayOf("3", "ANSWER,NOT,TRUTH", null),
            arrayOf("3", "ANSWER,NOT,CLEAR", null),
            arrayOf("3", "ANSWER,EQUAL,SUCCESS", null),
            arrayOf("3", "ALL,TRUTH,NOW", null),
            arrayOf("3", "ALL,CHAOS,DEATH", null),
            arrayOf("2", "TRUTH,MESSAGE", null),
            arrayOf("2", "SEEK,HARMONY", null),
            arrayOf("2", "QUESTION,AGAIN", null),
            arrayOf("2", "PERSUE,TRUTH", null),
            arrayOf("2", "PERSUE,PERFECTION", null),
            arrayOf("2", "PERFECT,BALANCE", null),
            arrayOf("2", "LOSE,IDEA", null),
            arrayOf("2", "FORGET,SUCCESS", null),
            arrayOf("2", "FORGET,IDEA", null),
            arrayOf("2", "DIFFICULT,ANSWER", null),
            arrayOf("2", "CONTEMPLATE,DEATH", null),
            arrayOf("2", "COMPLEX,ANSWER", null),
            arrayOf("2", "CHANGE,QUESTION", null),
            arrayOf("2", "ABANDON,SUCCESS", null),
            arrayOf("2", "ABANDON,IDEA", null),
            arrayOf("2", "ABANDON,ANSWER", null),
            arrayOf("1", "TRUTH", null),
            arrayOf("1", "RETREAT", null),
            arrayOf("1", "NO", null),
            arrayOf("1", "MYSTERY", null),
            arrayOf("1", "BARRIER", null)
    )

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table if not exists $TABLE_NAME1(id integer primary key autoincrement,name text not null,path text not null,correct_number integer,total_number integer);")
        db.execSQL("create table if not exists $TABLE_NAME2(id integer primary key autoincrement,level integer not null,sequence text not null,correctSeq text,correct_number integer,total_number integer);")

        db.beginTransaction()
        try {
            var stmt: SQLiteStatement

            stmt = db.compileStatement("insert into shapers(name, path) values(?, ?);")
            for (shaper in SHAPERS) {
                stmt.bindString(1, shaper[0])
                stmt.bindString(2, shaper[1])
                stmt.executeInsert()
            }
            stmt = db.compileStatement("insert into sets(level, sequence, correctSeq) values(?, ?, ?);")
            for (set in SETS) {
                stmt.bindString(1, set[0])
                stmt.bindString(2, set[1])
                if (set[2] != null) {
                    stmt.bindString(3, set[2])
                } else {
                    stmt.bindNull(3)
                }
                stmt.executeInsert()
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
        var c: Cursor = db.query(TABLE_NAME1, null, null, null, null, null, null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val contentValues = ContentValues()
            contentValues.put("correct_number", 0)
            contentValues.put("total_number", -1)
            db.update(TABLE_NAME1, contentValues, "id=${c.columnCount}", null)
            c.moveToNext()
        }
        c = db.query(TABLE_NAME2, null, null, null, null, null, null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val contentValues = ContentValues()
            contentValues.put("correct_number", 0)
            contentValues.put("total_number", -1)
            db.update(TABLE_NAME2, contentValues, "id=${c.columnCount}", null)
            c.moveToNext()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVer: Int, newVer: Int) {
        val tag = "DBHelper.onUpgrade"

        if (oldVer < 11) {
            db.execSQL("drop table if exists shapers;")
            db.execSQL("drop table if exists sets;")
            db.execSQL("drop table if exists weakShapers;")
            db.execSQL("drop table if exists weakSets;")

            onCreate(db)
        } else {
            val cursorTable1 = db.rawQuery("select * from $TABLE_NAME1;", null)
            cursorTable1.moveToFirst()
            db.execSQL("drop table if exists $TABLE_NAME1;")

            val cursorTable2 = db.rawQuery("select * from $TABLE_NAME2;", null)
            cursorTable2.moveToFirst()
            db.execSQL("drop table if exists $TABLE_NAME2;")

            onCreate(db)

            while (!cursorTable1.isAfterLast) {
                val name = "\"" + cursorTable1.getString(cursorTable1.getColumnIndex("name")) + "\""
                val contentValues = ContentValues()
                try {
                    contentValues.put("correct_number", cursorTable1.getInt(cursorTable1.getColumnIndex("correct_number")))
                } catch(e: Exception) {
                    Log.e(tag, e.message)
                }
                try {
                    contentValues.put("total_number", cursorTable1.getInt(cursorTable1.getColumnIndex("total_number")))
                } catch(e: Exception) {
                    Log.e(tag, e.message)
                }
                db.update(TABLE_NAME1, contentValues, "name=$name", null)
                cursorTable1.moveToNext()
            }

            while (!cursorTable2.isAfterLast) {
                val sequence = "\"" + cursorTable2.getInt(cursorTable2.getColumnIndex("sequence")) + "\""
                val contentValues = ContentValues()
                try {
                    contentValues.put("correct_number", cursorTable2.getInt(cursorTable2.getColumnIndex("correct_number")))
                } catch(e: Exception) {
                    Log.e(tag, e.message)
                }
                try {
                    contentValues.put("total_number", cursorTable2.getInt(cursorTable2.getColumnIndex("total_number")))
                } catch(e: Exception) {
                    Log.e(tag, e.message)
                }
                db.update(TABLE_NAME2, contentValues, "sequence=$sequence", null)
                cursorTable2.moveToNext()
            }
        }
    }
}