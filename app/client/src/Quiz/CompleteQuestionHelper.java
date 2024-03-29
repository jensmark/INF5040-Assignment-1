package Quiz;


/**
* Quiz/CompleteQuestionHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from quiz.idl
* Friday, September 20, 2013 11:55:54 AM CEST
*/


/* Data structure defining a complete question, which extends the Question structure
	   adding the correct alternatives for the question. */
abstract public class CompleteQuestionHelper
{
  private static String  _id = "IDL:Quiz/CompleteQuestion:1.0";


  public static void insert (org.omg.CORBA.Any a, Quiz.CompleteQuestion that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static Quiz.CompleteQuestion extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.ValueMember[] _members0 = new org.omg.CORBA.ValueMember[1];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          // ValueMember instance for correctAlternatives
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_char);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (Quiz.CompleteQuestionPackage.CharSeqHelper.id (), "CharSeq", _tcOf_members0);
          _members0[0] = new org.omg.CORBA.ValueMember ("correctAlternatives", 
              Quiz.CompleteQuestionPackage.CharSeqHelper.id (), 
              _id, 
              "1.0", 
              _tcOf_members0, 
              null, 
              org.omg.CORBA.PUBLIC_MEMBER.value);
          __typeCode = org.omg.CORBA.ORB.init ().create_value_tc (_id, "CompleteQuestion", org.omg.CORBA.VM_NONE.value, Quiz.QuestionHelper.type (), _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static Quiz.CompleteQuestion read (org.omg.CORBA.portable.InputStream istream)
  {
    return (Quiz.CompleteQuestion)((org.omg.CORBA_2_3.portable.InputStream) istream).read_value (id ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, Quiz.CompleteQuestion value)
  {
    ((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value (value, id ());
  }


}
