import com.camackenzie.exvi.core.model.ExerciseExperienceLevel
import com.camackenzie.exvi.core.model.Muscle
import com.camackenzie.exvi.core.util.EnumUtils
import com.camackenzie.exvi.core.util.Identifiable
import com.camackenzie.exvi.core.util.RawIdentifiable
import kotlin.test.Test
import kotlin.test.assertEquals

class TestEnumUtils {
    @Test
    fun testFormatName() {
        var name = EnumUtils.formatName(ExerciseExperienceLevel.Intermediate.toString())
        assertEquals("intermediate", name)
        name = EnumUtils.formatName(Muscle.BicepsBrachii.name)
        assertEquals("biceps brachii", name)
    }

    @Test
    fun testBuiltinFormatNames() {
        assertEquals("advanced", ExerciseExperienceLevel.Advanced.toString())
        assertEquals("recus abdominis", Muscle.RecusAbdominis.toString())
    }
}