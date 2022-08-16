package team.lodestar.fufo.client.ui;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.world.phys.Vec3;

public class UIMath {

    /**
     * Constructs a quaternion that will rotate a given start vector to an end vector along the shortest possible path
     * @param start The start vector the rotation begins at
     * @param end The end vector the rotation should end at
     * @return A rotation quaternion from vector start to end
     */
    public static Quaternion getQuaternionFromVectorRotation(Vec3 start, Vec3 end)
    {
        Vector3f cross = new Vector3f(start.cross(end));
        Quaternion Q = new Quaternion(cross.x(),cross.y(),cross.z(),1.0f+(float)start.dot(end));
        Q.normalize();
        return Q;
    }

}
