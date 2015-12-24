package native
package nir
package plugin

import scala.tools.nsc.Global

trait NativeBuiltins {
  val global: Global
  import global._, definitions._, rootMirror._

  lazy val PtrClass    = getRequiredClass("native.ffi.Ptr")
  lazy val ExternClass = getRequiredClass("native.ffi.extern")

  def isBuiltin(sym: Symbol): Boolean =
    UnboxValue.unapply(sym).nonEmpty                ||
    BoxValue.unapply(sym).nonEmpty                  ||
    ParseValue.unapply(sym).nonEmpty                ||
    ParseUnsignedValue.unapply(sym).nonEmpty        ||
    ToString.unapply(sym).nonEmpty                  ||
    BoxModuleToString.unapply(sym).nonEmpty         ||
    BoxModuleToUnsignedString.unapply(sym).nonEmpty

  object nnme {
    val booleanValue = TermName("booleanValue")
    val charValue    = TermName("charValue")
    val byteValue    = TermName("byteValue")
    val shortValue   = TermName("shortValue")
    val intValue     = TermName("intValue")
    val longValue    = TermName("longValue")
    val floatValue   = TermName("floatValue")
    val doubleValue  = TermName("doubleValue")

    val valueOf = TermName("valueOf")

    val parseBoolean = TermName("parseBoolean")
    val parseByte    = TermName("parseByte")
    val parseShort   = TermName("parseShort")
    val parseInt     = TermName("parseInt")
    val parseLong    = TermName("parseLong")
    val parseFloat   = TermName("parseFloat")
    val parseDouble  = TermName("parseDouble")

    def parseUnsignedInt  = TermName("parseUnsignedInt")
    def parseUnsignedLong = TermName("parseUnsignedLong")

    val toString_         = TermName("toString")
    val toUnsignedString_ = TermName("toUnsignedString")
  }

  lazy val JBoolean_booleanValue = getMemberMethod(BoxedBooleanClass,   nnme.booleanValue)
  lazy val JCharacter_charValue  = getMemberMethod(BoxedCharacterClass, nnme.charValue)

  lazy val JByte_byteValue   = getMemberMethod(BoxedByteClass, nnme.byteValue)
  lazy val JByte_shortValue  = getMemberMethod(BoxedByteClass, nnme.shortValue)
  lazy val JByte_intValue    = getMemberMethod(BoxedByteClass, nnme.intValue)
  lazy val JByte_longValue   = getMemberMethod(BoxedByteClass, nnme.longValue)
  lazy val JByte_floatValue  = getMemberMethod(BoxedByteClass, nnme.floatValue)
  lazy val JByte_doubleValue = getMemberMethod(BoxedByteClass, nnme.doubleValue)

  lazy val JShort_byteValue   = getMemberMethod(BoxedShortClass, nnme.byteValue)
  lazy val JShort_shortValue  = getMemberMethod(BoxedShortClass, nnme.shortValue)
  lazy val JShort_intValue    = getMemberMethod(BoxedShortClass, nnme.intValue)
  lazy val JShort_longValue   = getMemberMethod(BoxedShortClass, nnme.longValue)
  lazy val JShort_floatValue  = getMemberMethod(BoxedShortClass, nnme.floatValue)
  lazy val JShort_doubleValue = getMemberMethod(BoxedShortClass, nnme.doubleValue)

  lazy val JInt_byteValue   = getMemberMethod(BoxedIntClass, nnme.byteValue)
  lazy val JInt_shortValue  = getMemberMethod(BoxedIntClass, nnme.shortValue)
  lazy val JInt_intValue    = getMemberMethod(BoxedIntClass, nnme.intValue)
  lazy val JInt_longValue   = getMemberMethod(BoxedIntClass, nnme.longValue)
  lazy val JInt_floatValue  = getMemberMethod(BoxedIntClass, nnme.floatValue)
  lazy val JInt_doubleValue = getMemberMethod(BoxedIntClass, nnme.doubleValue)

  lazy val JLong_byteValue   = getMemberMethod(BoxedLongClass, nnme.byteValue)
  lazy val JLong_shortValue  = getMemberMethod(BoxedLongClass, nnme.shortValue)
  lazy val JLong_intValue    = getMemberMethod(BoxedLongClass, nnme.intValue)
  lazy val JLong_longValue   = getMemberMethod(BoxedLongClass, nnme.longValue)
  lazy val JLong_floatValue  = getMemberMethod(BoxedLongClass, nnme.floatValue)
  lazy val JLong_doubleValue = getMemberMethod(BoxedLongClass, nnme.doubleValue)

  lazy val JFloat_byteValue   = getMemberMethod(BoxedFloatClass, nnme.byteValue)
  lazy val JFloat_shortValue  = getMemberMethod(BoxedFloatClass, nnme.shortValue)
  lazy val JFloat_intValue    = getMemberMethod(BoxedFloatClass, nnme.intValue)
  lazy val JFloat_longValue   = getMemberMethod(BoxedFloatClass, nnme.longValue)
  lazy val JFloat_floatValue  = getMemberMethod(BoxedFloatClass, nnme.floatValue)
  lazy val JFloat_doubleValue = getMemberMethod(BoxedFloatClass, nnme.doubleValue)

  lazy val JDouble_byteValue   = getMemberMethod(BoxedDoubleClass, nnme.byteValue)
  lazy val JDouble_shortValue  = getMemberMethod(BoxedDoubleClass, nnme.shortValue)
  lazy val JDouble_intValue    = getMemberMethod(BoxedDoubleClass, nnme.intValue)
  lazy val JDouble_longValue   = getMemberMethod(BoxedDoubleClass, nnme.longValue)
  lazy val JDouble_floatValue  = getMemberMethod(BoxedDoubleClass, nnme.floatValue)
  lazy val JDouble_doubleValue = getMemberMethod(BoxedDoubleClass, nnme.doubleValue)

  object UnboxValue {
    def unapply(sym: Symbol): Option[(nir.Type, nir.Type)] = sym match {
      case JBoolean_booleanValue => Some((nir.Type.BooleanClass, nir.Type.Bool))
      case JCharacter_charValue  => Some((nir.Type.CharacterClass, nir.Type.I16))

      case JByte_byteValue   => Some((nir.Type.ByteClass, nir.Type.I8))
      case JByte_shortValue  => Some((nir.Type.ByteClass, nir.Type.I16))
      case JByte_intValue    => Some((nir.Type.ByteClass, nir.Type.I32))
      case JByte_longValue   => Some((nir.Type.ByteClass, nir.Type.I64))
      case JByte_floatValue  => Some((nir.Type.ByteClass, nir.Type.F32))
      case JByte_doubleValue => Some((nir.Type.ByteClass, nir.Type.F64))

      case JShort_byteValue   => Some((nir.Type.ShortClass, nir.Type.I8))
      case JShort_shortValue  => Some((nir.Type.ShortClass, nir.Type.I16))
      case JShort_intValue    => Some((nir.Type.ShortClass, nir.Type.I32))
      case JShort_longValue   => Some((nir.Type.ShortClass, nir.Type.I64))
      case JShort_floatValue  => Some((nir.Type.ShortClass, nir.Type.F32))
      case JShort_doubleValue => Some((nir.Type.ShortClass, nir.Type.F64))

      case JInt_byteValue   => Some((nir.Type.IntegerClass, nir.Type.I8))
      case JInt_shortValue  => Some((nir.Type.IntegerClass, nir.Type.I16))
      case JInt_intValue    => Some((nir.Type.IntegerClass, nir.Type.I32))
      case JInt_longValue   => Some((nir.Type.IntegerClass, nir.Type.I64))
      case JInt_floatValue  => Some((nir.Type.IntegerClass, nir.Type.F32))
      case JInt_doubleValue => Some((nir.Type.IntegerClass, nir.Type.F64))

      case JLong_byteValue   => Some((nir.Type.LongClass, nir.Type.I8))
      case JLong_shortValue  => Some((nir.Type.LongClass, nir.Type.I16))
      case JLong_intValue    => Some((nir.Type.LongClass, nir.Type.I32))
      case JLong_longValue   => Some((nir.Type.LongClass, nir.Type.I64))
      case JLong_floatValue  => Some((nir.Type.LongClass, nir.Type.F32))
      case JLong_doubleValue => Some((nir.Type.LongClass, nir.Type.F64))

      case JFloat_byteValue   => Some((nir.Type.FloatClass, nir.Type.I8))
      case JFloat_shortValue  => Some((nir.Type.FloatClass, nir.Type.I16))
      case JFloat_intValue    => Some((nir.Type.FloatClass, nir.Type.I32))
      case JFloat_longValue   => Some((nir.Type.FloatClass, nir.Type.I64))
      case JFloat_floatValue  => Some((nir.Type.FloatClass, nir.Type.F32))
      case JFloat_doubleValue => Some((nir.Type.FloatClass, nir.Type.F64))

      case JDouble_byteValue   => Some((nir.Type.DoubleClass, nir.Type.I8))
      case JDouble_shortValue  => Some((nir.Type.DoubleClass, nir.Type.I16))
      case JDouble_intValue    => Some((nir.Type.DoubleClass, nir.Type.I32))
      case JDouble_longValue   => Some((nir.Type.DoubleClass, nir.Type.I64))
      case JDouble_floatValue  => Some((nir.Type.DoubleClass, nir.Type.F32))
      case JDouble_doubleValue => Some((nir.Type.DoubleClass, nir.Type.F64))

      case _                   => None
    }
  }

  lazy val JBoolean_valueOf   = getMemberMethod(BoxedBooleanClass.companion,   nnme.valueOf)
  lazy val JCharacter_valueOf = getMemberMethod(BoxedCharacterClass.companion, nnme.valueOf)
  lazy val JByte_valueOf      = getMemberMethod(BoxedByteClass.companion,      nnme.valueOf)
  lazy val JShort_valueOf     = getMemberMethod(BoxedShortClass.companion,     nnme.valueOf)
  lazy val JInteger_valueOf   = getMemberMethod(BoxedIntClass.companion,       nnme.valueOf)
  lazy val JLong_valueOf      = getMemberMethod(BoxedLongClass.companion,      nnme.valueOf)
  lazy val JFloat_valueOf     = getMemberMethod(BoxedFloatClass.companion,     nnme.valueOf)
  lazy val JDouble_valueOf    = getMemberMethod(BoxedDoubleClass.companion,    nnme.valueOf)

  object BoxValue {
    def unapply(sym: Symbol): Option[nir.Type] = {
           if (  JBoolean_valueOf.alternatives.contains(sym)) Some(nir.Type.BooleanClass)
      else if (JCharacter_valueOf.alternatives.contains(sym)) Some(nir.Type.CharacterClass)
      else if (     JByte_valueOf.alternatives.contains(sym)) Some(nir.Type.ByteClass)
      else if (    JShort_valueOf.alternatives.contains(sym)) Some(nir.Type.ShortClass)
      else if (  JInteger_valueOf.alternatives.contains(sym)) Some(nir.Type.IntegerClass)
      else if (     JLong_valueOf.alternatives.contains(sym)) Some(nir.Type.LongClass)
      else if (    JFloat_valueOf.alternatives.contains(sym)) Some(nir.Type.FloatClass)
      else if (   JDouble_valueOf.alternatives.contains(sym)) Some(nir.Type.DoubleClass)
      else                                                    None
    }
  }

  lazy val JBooleanModule_parseBoolean = getMemberMethod(BoxedBooleanClass.companion, nnme.parseBoolean)
  lazy val JByteModule_parseByte       = getMemberMethod(BoxedByteClass.companion,    nnme.parseByte)
  lazy val JShortModule_parseShort     = getMemberMethod(BoxedShortClass.companion,   nnme.parseShort)
  lazy val JIntegerModule_parseInt     = getMemberMethod(BoxedIntClass.companion,     nnme.parseInt)
  lazy val JLongModule_parseLong       = getMemberMethod(BoxedLongClass.companion,    nnme.parseLong)
  lazy val JFloatModule_parseFloat     = getMemberMethod(BoxedFloatClass.companion,   nnme.parseFloat)
  lazy val JDoubleModule_parseDouble   = getMemberMethod(BoxedDoubleClass.companion,  nnme.parseDouble)

  object ParseValue {
    def unapply(sym: Symbol): Option[nir.Type] = {
           if (JBooleanModule_parseBoolean.alternatives.contains(sym)) Some((nir.Type.Bool))
      else if (      JByteModule_parseByte.alternatives.contains(sym)) Some((nir.Type.I8))
      else if (    JShortModule_parseShort.alternatives.contains(sym)) Some((nir.Type.I16))
      else if (    JIntegerModule_parseInt.alternatives.contains(sym)) Some((nir.Type.I32))
      else if (      JLongModule_parseLong.alternatives.contains(sym)) Some((nir.Type.I64))
      else if (    JFloatModule_parseFloat.alternatives.contains(sym)) Some((nir.Type.F32))
      else if (  JDoubleModule_parseDouble.alternatives.contains(sym)) Some((nir.Type.F64))
      else                                                             None
    }
  }

  lazy val JIntegerModule_parseUnsignedInt = getMemberMethod(BoxedIntClass.companion,  nnme.parseUnsignedInt)
  lazy val JLongModule_parseUnsignedLong   = getMemberMethod(BoxedLongClass.companion, nnme.parseUnsignedLong)

  object ParseUnsignedValue {
    def unapply(sym: Symbol): Option[nir.Type] = {
           if (JIntegerModule_parseUnsignedInt.alternatives.contains(sym)) Some((nir.Type.I32))
      else if (  JLongModule_parseUnsignedLong.alternatives.contains(sym)) Some((nir.Type.I64))
      else                                                                 None
    }
  }

  lazy val JObject_toString    = getDecl(ObjectClass        , nnme.toString_)
  lazy val JBoolean_toString   = getDecl(BoxedBooleanClass  , nnme.toString_)
  lazy val JCharacter_toString = getDecl(BoxedCharacterClass, nnme.toString_)
  lazy val JByte_toString      = getDecl(BoxedByteClass     , nnme.toString_)
  lazy val JShort_toString     = getDecl(BoxedShortClass    , nnme.toString_)
  lazy val JInteger_toString   = getDecl(BoxedIntClass      , nnme.toString_)
  lazy val JLong_toString      = getDecl(BoxedLongClass     , nnme.toString_)
  lazy val JFloat_toString     = getDecl(BoxedFloatClass    , nnme.toString_)
  lazy val JDouble_toString    = getDecl(BoxedDoubleClass   , nnme.toString_)

  object ToString {
    def unapply(sym: Symbol): Option[nir.Type] = sym match {
      case JObject_toString  => Some(nir.Type.ObjectClass)
      case JBoolean_toString => Some(nir.Type.BooleanClass)
      case JByte_toString    => Some(nir.Type.ByteClass)
      case JShort_toString   => Some(nir.Type.ShortClass)
      case JInteger_toString => Some(nir.Type.IntegerClass)
      case JLong_toString    => Some(nir.Type.LongClass)
      case JFloat_toString   => Some(nir.Type.FloatClass)
      case JDouble_toString  => Some(nir.Type.DoubleClass)
      case _                 => None
    }
  }

  lazy val JBooleanModule_toString   = getMemberMethod(BoxedBooleanClass.companion,   nnme.toString_)
  lazy val JCharacterModule_toString = getMemberMethod(BoxedCharacterClass.companion, nnme.toString_)
  lazy val JByteModule_toString      = getMemberMethod(BoxedByteClass.companion,      nnme.toString_)
  lazy val JShortModule_toString     = getMemberMethod(BoxedShortClass.companion,     nnme.toString_)
  lazy val JIntegerModule_toString   = getMemberMethod(BoxedIntClass.companion,       nnme.toString_)
  lazy val JLongModule_toString      = getMemberMethod(BoxedLongClass.companion,      nnme.toString_)
  lazy val JFloatModule_toString     = getMemberMethod(BoxedFloatClass.companion,     nnme.toString_)
  lazy val JDoubleModule_toString    = getMemberMethod(BoxedDoubleClass.companion,    nnme.toString_)

  object BoxModuleToString {
    def unapply(sym: Symbol): Option[nir.Type] = {
           if (  JBooleanModule_toString.alternatives.contains(sym)) Some((nir.Type.BooleanClass))
      else if (JCharacterModule_toString.alternatives.contains(sym)) Some((nir.Type.CharacterClass))
      else if (     JByteModule_toString.alternatives.contains(sym)) Some((nir.Type.ByteClass))
      else if (    JShortModule_toString.alternatives.contains(sym)) Some((nir.Type.ShortClass))
      else if (  JIntegerModule_toString.alternatives.contains(sym)) Some((nir.Type.IntegerClass))
      else if (     JLongModule_toString.alternatives.contains(sym)) Some((nir.Type.LongClass))
      else if (    JFloatModule_toString.alternatives.contains(sym)) Some((nir.Type.FloatClass))
      else if (   JDoubleModule_toString.alternatives.contains(sym)) Some((nir.Type.DoubleClass))
      else                                                           None
    }
  }

  lazy val JIntegerModule_toUnsignedString = getMemberMethod(BoxedIntClass.companion,  nnme.toUnsignedString_)
  lazy val JLongModule_toUnsignedString    = getMemberMethod(BoxedLongClass.companion, nnme.toUnsignedString_)

  object BoxModuleToUnsignedString {
    def unapply(sym: Symbol): Option[nir.Type] = {
           if (JIntegerModule_toUnsignedString.alternatives.contains(sym)) Some((nir.Type.IntegerClass))
      else if (   JLongModule_toUnsignedString.alternatives.contains(sym)) Some((nir.Type.LongClass))
      else                                                                 None
    }
  }
}