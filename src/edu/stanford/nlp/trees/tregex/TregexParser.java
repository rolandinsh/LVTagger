/* Generated By:JavaCC: Do not edit this line. TregexParser.java */
package edu.stanford.nlp.trees.tregex;
// all generated classes are in this package

import edu.stanford.nlp.util.Function;
import edu.stanford.nlp.util.Pair;
import edu.stanford.nlp.trees.HeadFinder;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class TregexParser implements TregexParserConstants {

  // this is so we can tell, at any point during the parse
  // whether we are under a negation, which we need to know
  // because labeling nodes under negation is illegal
  private boolean underNegation = false;

  private Function<String, String> basicCatFunction =
    TregexPatternCompiler.DEFAULT_BASIC_CAT_FUNCTION;

  private HeadFinder headFinder =
    TregexPatternCompiler.DEFAULT_HEAD_FINDER;

  // keep track of which variables we've seen, so that we can reject
  // some nonsense patterns such as ones that reset variables or link
  // to variables that haven't been set
  private Set<String> knownVariables = new HashSet<String>();

  public TregexParser(java.io.Reader stream,
                      Function<String, String> basicCatFunction,
                      HeadFinder headFinder) {
    this(stream);
    this.basicCatFunction = basicCatFunction;
    this.headFinder = headFinder;
  }

// the grammar starts here
// each of these BNF rules will be converted into a function
// first expr is return val- passed up the tree after a production
  final public TregexPattern Root() throws ParseException {
  TregexPattern node;
    node = SubNode(Relation.ROOT);
    jj_consume_token(11);
    {if (true) return node;}
    throw new Error("Missing return statement in function");
  }

// passing arguments down the tree - in this case the relation that
// pertains to this node gets passed all the way down to the Description node
  final public TregexPattern Node(Relation r) throws ParseException {
  TregexPattern node;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 12:
      jj_consume_token(12);
      node = SubNode(r);
      jj_consume_token(13);
      break;
    case IDENTIFIER:
    case BLANK:
    case REGEX:
    case 14:
    case 15:
    case 18:
    case 19:
      node = ModDescription(r);
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return node;}
    throw new Error("Missing return statement in function");
  }

  final public DescriptionPattern SubNode(Relation r) throws ParseException {
  DescriptionPattern result = null;
  TregexPattern child = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 12:
      jj_consume_token(12);
      result = SubNode(r);
      jj_consume_token(13);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case RELATION:
      case REL_W_STR_ARG:
      case 14:
      case 22:
      case 23:
        child = ChildrenDisj();
        break;
      default:
        jj_la1[1] = jj_gen;
        ;
      }
      if(child != null) {
        List<TregexPattern> newChildren = new ArrayList<TregexPattern>();
        newChildren.addAll(result.getChildren());
        newChildren.add(child);
        result.setChild(new CoordinationPattern(newChildren,true));
      }
      {if (true) return result;}
      break;
    case IDENTIFIER:
    case BLANK:
    case REGEX:
    case 14:
    case 15:
    case 18:
    case 19:
      result = ModDescription(r);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case RELATION:
      case REL_W_STR_ARG:
      case 14:
      case 22:
      case 23:
        child = ChildrenDisj();
        break;
      default:
        jj_la1[2] = jj_gen;
        ;
      }
      if (child != null) result.setChild(child);
      {if (true) return result;}
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public DescriptionPattern ModDescription(Relation r) throws ParseException {
  DescriptionPattern node;
  boolean neg = false, cat = false;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 14:
      jj_consume_token(14);
             neg = true;
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 15:
      jj_consume_token(15);
                                      cat = true;
      break;
    default:
      jj_la1[5] = jj_gen;
      ;
    }
    node = Description(r, neg, cat);
    {if (true) return node;}
    throw new Error("Missing return statement in function");
  }

  final public DescriptionPattern Description(Relation r, boolean negateDesc, boolean cat) throws ParseException {
  Token desc = null;
  Token name = null;
  Token linkedName = null;
  boolean link = false;
  Token groupNum;
  Token groupVar;
  List<Pair<Integer,String>> varGroups = new ArrayList<Pair<Integer,String>>();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENTIFIER:
    case BLANK:
    case REGEX:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENTIFIER:
        desc = jj_consume_token(IDENTIFIER);
        break;
      case REGEX:
        desc = jj_consume_token(REGEX);
        break;
      case BLANK:
        desc = jj_consume_token(BLANK);
        break;
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 16:
          ;
          break;
        default:
          jj_la1[7] = jj_gen;
          break label_1;
        }
        jj_consume_token(16);
        groupNum = jj_consume_token(NUMBER);
        jj_consume_token(17);
        groupVar = jj_consume_token(IDENTIFIER);
        varGroups.add(new Pair<Integer,String>(Integer.parseInt(groupNum.image),groupVar.image));
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 18:
        jj_consume_token(18);
        name = jj_consume_token(IDENTIFIER);
          if (knownVariables.contains(name.image)) {
            {if (true) throw new ParseException("Variable " + name.image + " has been declared twice, which makes no sense");}
          } else {
            knownVariables.add(name.image);
          }
          if (underNegation)
            {if (true) throw new ParseException("No named tregex nodes allowed in the scope of negation.");}
        break;
      default:
        jj_la1[8] = jj_gen;
        ;
      }
      break;
    case 19:
      jj_consume_token(19);
      linkedName = jj_consume_token(IDENTIFIER);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 18:
        jj_consume_token(18);
        name = jj_consume_token(IDENTIFIER);
        break;
      default:
        jj_la1[9] = jj_gen;
        ;
      }
        if (!knownVariables.contains(linkedName.image)) {
          {if (true) throw new ParseException("Variable " + linkedName.image +
                                   " was referenced before it was declared");}
        }
        if (name != null) {
          if (knownVariables.contains(name.image)) {
            {if (true) throw new ParseException("Variable " + name.image + " has been declared twice, which makes no sense");}
          } else {
            knownVariables.add(name.image);
          }
        }
        link = true;
      break;
    case 18:
      jj_consume_token(18);
      name = jj_consume_token(IDENTIFIER);
        if (!knownVariables.contains(name.image)) {
          {if (true) throw new ParseException("Variable " + name.image +
                                   " was referenced before it was declared");}
        }
      break;
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    DescriptionPattern ret = new DescriptionPattern(r, negateDesc, desc != null ? desc.image : null, name != null ? name.image : null, cat, basicCatFunction, varGroups, link, linkedName != null ? linkedName.image : null);
    {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

  final public TregexPattern ChildrenDisj() throws ParseException {
  TregexPattern child;
  List<TregexPattern> children = new ArrayList<TregexPattern>();
  // When we keep track of the known variables to assert that
  // variables are not redefined, or that links are only set to known
  // variables, we want to separate those done in different parts of the
  // disjunction.  Variables set in one part won't be set in the next
  // part if it gets there, since disjunctions exit once known.
  Set<String> originalKnownVariables = new HashSet<String>(knownVariables);
  // However, we want to keep track of all the known variables, so that after
  // the disjunction is over, we know them all.
  Set<String> allKnownVariables = new HashSet<String>(knownVariables);
    child = ChildrenConj();
      children.add(child);
      allKnownVariables.addAll(knownVariables);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 20:
        ;
        break;
      default:
        jj_la1[11] = jj_gen;
        break label_2;
      }
        knownVariables = new HashSet<String>(originalKnownVariables);
      jj_consume_token(20);
      child = ChildrenConj();
      children.add(child);
      allKnownVariables.addAll(knownVariables);
    }
    knownVariables = allKnownVariables;
    if (children.size() == 1)
      {if (true) return child;}
    else
      {if (true) return new CoordinationPattern(children, false);}
    throw new Error("Missing return statement in function");
  }

  final public TregexPattern ChildrenConj() throws ParseException {
  TregexPattern child;
  List<TregexPattern> children = new ArrayList<TregexPattern>();
    child = ModChild();
                             children.add(child);
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case RELATION:
      case REL_W_STR_ARG:
      case 14:
      case 21:
      case 22:
      case 23:
        ;
        break;
      default:
        jj_la1[12] = jj_gen;
        break label_3;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 21:
        jj_consume_token(21);
        break;
      default:
        jj_la1[13] = jj_gen;
        ;
      }
      child = ModChild();
                                      children.add(child);
    }
    if (children.size() == 1)
        {if (true) return child;}
      else
        {if (true) return new CoordinationPattern(children, true);}
    throw new Error("Missing return statement in function");
  }

  final public TregexPattern ModChild() throws ParseException {
  TregexPattern child;
  boolean startUnderNeg;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case RELATION:
    case REL_W_STR_ARG:
    case 23:
      child = Child();
      break;
    case 14:
      jj_consume_token(14);
          startUnderNeg = underNegation;
          underNegation = true;
      child = Child();
            underNegation = startUnderNeg;
        child.negate();
      break;
    case 22:
      jj_consume_token(22);
      child = Child();
                                child.makeOptional();
      break;
    default:
      jj_la1[14] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return child;}
    throw new Error("Missing return statement in function");
  }

  final public TregexPattern Child() throws ParseException {
  TregexPattern child;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 23:
      jj_consume_token(23);
      child = ChildrenDisj();
      jj_consume_token(24);
      break;
    case RELATION:
    case REL_W_STR_ARG:
      child = Relation();
      break;
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return child;}
    throw new Error("Missing return statement in function");
  }

  final public TregexPattern Relation() throws ParseException {
  Token t, strArg = null, numArg = null, negation = null, cat = null;
  // the easiest way to check if an optional production was used
  // is to set the token to null and then check it later
  Relation r;
  TregexPattern child;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case RELATION:
      t = jj_consume_token(RELATION);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NUMBER:
        numArg = jj_consume_token(NUMBER);
        break;
      default:
        jj_la1[16] = jj_gen;
        ;
      }
      break;
    case REL_W_STR_ARG:
      t = jj_consume_token(REL_W_STR_ARG);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 12:
        jj_consume_token(12);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 14:
          negation = jj_consume_token(14);
          break;
        default:
          jj_la1[17] = jj_gen;
          ;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 15:
          cat = jj_consume_token(15);
          break;
        default:
          jj_la1[18] = jj_gen;
          ;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case REGEX:
          strArg = jj_consume_token(REGEX);
          break;
        case IDENTIFIER:
          strArg = jj_consume_token(IDENTIFIER);
          break;
        case BLANK:
          strArg = jj_consume_token(BLANK);
          break;
        default:
          jj_la1[19] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        jj_consume_token(13);
        break;
      case REGEX:
      case 14:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 14:
          negation = jj_consume_token(14);
          break;
        default:
          jj_la1[20] = jj_gen;
          ;
        }
        strArg = jj_consume_token(REGEX);
        break;
      default:
        jj_la1[21] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[22] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
      if (strArg != null) {
        String negStr = negation == null ? "": "!";
        String catStr = cat == null ? "": "@";
        r = Relation.getRelation(t.image, negStr + catStr + strArg.image,
                                 basicCatFunction, headFinder);
      } else if (numArg != null) {
        if (t.image.endsWith("-")) {
          t.image = t.image.substring(0, t.image.length()-1);
          numArg.image = "-" + numArg.image;
        }
        r = Relation.getRelation(t.image, numArg.image,
                                 basicCatFunction, headFinder);
      } else {
        r = Relation.getRelation(t.image, basicCatFunction, headFinder);
      }
    child = Node(r);
    {if (true) return child;}
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public TregexParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[23];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0xcd380,0xc04030,0xc04030,0xcd380,0x4000,0x8000,0x380,0x10000,0x40000,0x40000,0xc0380,0x100000,0xe04030,0x200000,0xc04030,0x800030,0x40,0x4000,0x8000,0x380,0x4000,0x5200,0x30,};
   }

  /** Constructor with InputStream. */
  public TregexParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public TregexParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new TregexParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public TregexParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new TregexParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public TregexParser(TregexParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(TregexParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[25];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 23; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 25; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
