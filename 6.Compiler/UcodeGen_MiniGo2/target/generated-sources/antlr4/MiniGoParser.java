// Generated from MiniGo.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MiniGoParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, VOID=20, VAR=21, FUNC=22, FMT=23, INT=24, FOR=25, 
		IF=26, ELSE=27, RETURN=28, OR=29, AND=30, LE=31, GE=32, EQ=33, NE=34, 
		PRINTLN=35, PRINT=36, IDENT=37, LITERAL=38, DecimalConstant=39, OctalConstant=40, 
		HexadecimalConstant=41, WS=42;
	public static final int
		RULE_program = 0, RULE_decl = 1, RULE_var_decl = 2, RULE_type_spec = 3, 
		RULE_fun_decl = 4, RULE_params = 5, RULE_param = 6, RULE_stmt = 7, RULE_expr_stmt = 8, 
		RULE_assign_stmt = 9, RULE_compound_stmt = 10, RULE_if_stmt = 11, RULE_for_stmt = 12, 
		RULE_return_stmt = 13, RULE_print_stmt = 14, RULE_local_decl = 15, RULE_expr = 16, 
		RULE_args = 17;
	public static final String[] ruleNames = {
		"program", "decl", "var_decl", "type_spec", "fun_decl", "params", "param", 
		"stmt", "expr_stmt", "assign_stmt", "compound_stmt", "if_stmt", "for_stmt", 
		"return_stmt", "print_stmt", "local_decl", "expr", "args"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "','", "'['", "']'", "'('", "')'", "'='", "'{'", "'}'", "'.'", "'-'", 
		"'+'", "'--'", "'++'", "'!'", "'*'", "'/'", "'%'", "'<'", "'>'", "'void'", 
		"'var'", "'func'", "'fmt'", "'int'", "'for'", "'if'", "'else'", "'return'", 
		"'or'", "'and'", "'<='", "'>='", "'=='", "'!='", "'Println'", "'Print'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, "VOID", "VAR", "FUNC", 
		"FMT", "INT", "FOR", "IF", "ELSE", "RETURN", "OR", "AND", "LE", "GE", 
		"EQ", "NE", "PRINTLN", "PRINT", "IDENT", "LITERAL", "DecimalConstant", 
		"OctalConstant", "HexadecimalConstant", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MiniGo.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MiniGoParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public List<DeclContext> decl() {
			return getRuleContexts(DeclContext.class);
		}
		public DeclContext decl(int i) {
			return getRuleContext(DeclContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(36);
				decl();
				}
				}
				setState(39); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==VAR || _la==FUNC );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclContext extends ParserRuleContext {
		public Var_declContext var_decl() {
			return getRuleContext(Var_declContext.class,0);
		}
		public Fun_declContext fun_decl() {
			return getRuleContext(Fun_declContext.class,0);
		}
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitDecl(this);
		}
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_decl);
		try {
			setState(43);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VAR:
				enterOuterAlt(_localctx, 1);
				{
				setState(41);
				var_decl();
				}
				break;
			case FUNC:
				enterOuterAlt(_localctx, 2);
				{
				setState(42);
				fun_decl();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Var_declContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(MiniGoParser.VAR, 0); }
		public List<TerminalNode> IDENT() { return getTokens(MiniGoParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(MiniGoParser.IDENT, i);
		}
		public Type_specContext type_spec() {
			return getRuleContext(Type_specContext.class,0);
		}
		public TerminalNode LITERAL() { return getToken(MiniGoParser.LITERAL, 0); }
		public Var_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterVar_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitVar_decl(this);
		}
	}

	public final Var_declContext var_decl() throws RecognitionException {
		Var_declContext _localctx = new Var_declContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_var_decl);
		try {
			setState(59);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(45);
				match(VAR);
				setState(46);
				match(IDENT);
				setState(47);
				type_spec();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(48);
				match(VAR);
				setState(49);
				match(IDENT);
				setState(50);
				match(T__0);
				setState(51);
				match(IDENT);
				setState(52);
				type_spec();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(53);
				match(VAR);
				setState(54);
				match(IDENT);
				setState(55);
				match(T__1);
				setState(56);
				match(LITERAL);
				setState(57);
				match(T__2);
				setState(58);
				type_spec();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Type_specContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(MiniGoParser.INT, 0); }
		public TerminalNode VOID() { return getToken(MiniGoParser.VOID, 0); }
		public Type_specContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_spec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterType_spec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitType_spec(this);
		}
	}

	public final Type_specContext type_spec() throws RecognitionException {
		Type_specContext _localctx = new Type_specContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_type_spec);
		try {
			setState(64);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(61);
				match(INT);
				}
				break;
			case VOID:
				enterOuterAlt(_localctx, 2);
				{
				setState(62);
				match(VOID);
				}
				break;
			case EOF:
			case T__0:
			case T__3:
			case T__4:
			case T__5:
			case T__6:
			case T__7:
			case T__9:
			case T__10:
			case T__11:
			case T__12:
			case T__13:
			case VAR:
			case FUNC:
			case FMT:
			case FOR:
			case IF:
			case RETURN:
			case IDENT:
			case LITERAL:
				enterOuterAlt(_localctx, 3);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Fun_declContext extends ParserRuleContext {
		public TerminalNode FUNC() { return getToken(MiniGoParser.FUNC, 0); }
		public TerminalNode IDENT() { return getToken(MiniGoParser.IDENT, 0); }
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public List<Type_specContext> type_spec() {
			return getRuleContexts(Type_specContext.class);
		}
		public Type_specContext type_spec(int i) {
			return getRuleContext(Type_specContext.class,i);
		}
		public Compound_stmtContext compound_stmt() {
			return getRuleContext(Compound_stmtContext.class,0);
		}
		public Fun_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fun_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterFun_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitFun_decl(this);
		}
	}

	public final Fun_declContext fun_decl() throws RecognitionException {
		Fun_declContext _localctx = new Fun_declContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_fun_decl);
		try {
			setState(86);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(66);
				match(FUNC);
				setState(67);
				match(IDENT);
				setState(68);
				match(T__3);
				setState(69);
				params();
				setState(70);
				match(T__4);
				setState(71);
				type_spec();
				setState(72);
				compound_stmt();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(74);
				match(FUNC);
				setState(75);
				match(IDENT);
				setState(76);
				match(T__3);
				setState(77);
				params();
				setState(78);
				match(T__4);
				setState(79);
				match(T__3);
				setState(80);
				type_spec();
				setState(81);
				match(T__0);
				setState(82);
				type_spec();
				setState(83);
				match(T__4);
				setState(84);
				compound_stmt();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamsContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterParams(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitParams(this);
		}
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_params);
		int _la;
		try {
			setState(97);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				}
				break;
			case IDENT:
				enterOuterAlt(_localctx, 2);
				{
				setState(89);
				param();
				setState(94);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(90);
					match(T__0);
					setState(91);
					param();
					}
					}
					setState(96);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamContext extends ParserRuleContext {
		public TerminalNode IDENT() { return getToken(MiniGoParser.IDENT, 0); }
		public Type_specContext type_spec() {
			return getRuleContext(Type_specContext.class,0);
		}
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitParam(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_param);
		try {
			setState(105);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(99);
				match(IDENT);
				setState(100);
				type_spec();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(101);
				match(IDENT);
				setState(102);
				match(T__1);
				setState(103);
				match(T__2);
				setState(104);
				type_spec();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmtContext extends ParserRuleContext {
		public Expr_stmtContext expr_stmt() {
			return getRuleContext(Expr_stmtContext.class,0);
		}
		public Compound_stmtContext compound_stmt() {
			return getRuleContext(Compound_stmtContext.class,0);
		}
		public Assign_stmtContext assign_stmt() {
			return getRuleContext(Assign_stmtContext.class,0);
		}
		public If_stmtContext if_stmt() {
			return getRuleContext(If_stmtContext.class,0);
		}
		public For_stmtContext for_stmt() {
			return getRuleContext(For_stmtContext.class,0);
		}
		public Return_stmtContext return_stmt() {
			return getRuleContext(Return_stmtContext.class,0);
		}
		public Print_stmtContext print_stmt() {
			return getRuleContext(Print_stmtContext.class,0);
		}
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitStmt(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_stmt);
		try {
			setState(114);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(107);
				expr_stmt();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(108);
				compound_stmt();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(109);
				assign_stmt();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(110);
				if_stmt();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(111);
				for_stmt();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(112);
				return_stmt();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(113);
				print_stmt();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Expr_stmtContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Expr_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterExpr_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitExpr_stmt(this);
		}
	}

	public final Expr_stmtContext expr_stmt() throws RecognitionException {
		Expr_stmtContext _localctx = new Expr_stmtContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_expr_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assign_stmtContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(MiniGoParser.VAR, 0); }
		public List<TerminalNode> IDENT() { return getTokens(MiniGoParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(MiniGoParser.IDENT, i);
		}
		public Type_specContext type_spec() {
			return getRuleContext(Type_specContext.class,0);
		}
		public List<TerminalNode> LITERAL() { return getTokens(MiniGoParser.LITERAL); }
		public TerminalNode LITERAL(int i) {
			return getToken(MiniGoParser.LITERAL, i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public Assign_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterAssign_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitAssign_stmt(this);
		}
	}

	public final Assign_stmtContext assign_stmt() throws RecognitionException {
		Assign_stmtContext _localctx = new Assign_stmtContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_assign_stmt);
		try {
			setState(146);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(118);
				match(VAR);
				setState(119);
				match(IDENT);
				setState(120);
				match(T__0);
				setState(121);
				match(IDENT);
				setState(122);
				type_spec();
				setState(123);
				match(T__5);
				setState(124);
				match(LITERAL);
				setState(125);
				match(T__0);
				setState(126);
				match(LITERAL);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(128);
				match(VAR);
				setState(129);
				match(IDENT);
				setState(130);
				type_spec();
				setState(131);
				match(T__5);
				setState(132);
				expr(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(134);
				match(IDENT);
				setState(135);
				type_spec();
				setState(136);
				match(T__5);
				setState(137);
				expr(0);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(139);
				match(IDENT);
				setState(140);
				match(T__1);
				setState(141);
				expr(0);
				setState(142);
				match(T__2);
				setState(143);
				match(T__5);
				setState(144);
				expr(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Compound_stmtContext extends ParserRuleContext {
		public List<Local_declContext> local_decl() {
			return getRuleContexts(Local_declContext.class);
		}
		public Local_declContext local_decl(int i) {
			return getRuleContext(Local_declContext.class,i);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public Compound_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compound_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterCompound_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitCompound_stmt(this);
		}
	}

	public final Compound_stmtContext compound_stmt() throws RecognitionException {
		Compound_stmtContext _localctx = new Compound_stmtContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_compound_stmt);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			match(T__6);
			setState(152);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(149);
					local_decl();
					}
					} 
				}
				setState(154);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			setState(158);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__6) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << VAR) | (1L << FMT) | (1L << FOR) | (1L << IF) | (1L << RETURN) | (1L << IDENT) | (1L << LITERAL))) != 0)) {
				{
				{
				setState(155);
				stmt();
				}
				}
				setState(160);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(161);
			match(T__7);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class If_stmtContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(MiniGoParser.IF, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<Compound_stmtContext> compound_stmt() {
			return getRuleContexts(Compound_stmtContext.class);
		}
		public Compound_stmtContext compound_stmt(int i) {
			return getRuleContext(Compound_stmtContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(MiniGoParser.ELSE, 0); }
		public If_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterIf_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitIf_stmt(this);
		}
	}

	public final If_stmtContext if_stmt() throws RecognitionException {
		If_stmtContext _localctx = new If_stmtContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_if_stmt);
		try {
			setState(173);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(163);
				match(IF);
				setState(164);
				expr(0);
				setState(165);
				compound_stmt();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(167);
				match(IF);
				setState(168);
				expr(0);
				setState(169);
				compound_stmt();
				setState(170);
				match(ELSE);
				setState(171);
				compound_stmt();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class For_stmtContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(MiniGoParser.FOR, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Compound_stmtContext compound_stmt() {
			return getRuleContext(Compound_stmtContext.class,0);
		}
		public For_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_for_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterFor_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitFor_stmt(this);
		}
	}

	public final For_stmtContext for_stmt() throws RecognitionException {
		For_stmtContext _localctx = new For_stmtContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_for_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			match(FOR);
			setState(176);
			expr(0);
			setState(177);
			compound_stmt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Return_stmtContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(MiniGoParser.RETURN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public Return_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_return_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterReturn_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitReturn_stmt(this);
		}
	}

	public final Return_stmtContext return_stmt() throws RecognitionException {
		Return_stmtContext _localctx = new Return_stmtContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_return_stmt);
		try {
			setState(187);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(179);
				match(RETURN);
				setState(180);
				expr(0);
				setState(181);
				match(T__0);
				setState(182);
				expr(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(184);
				match(RETURN);
				setState(185);
				expr(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(186);
				match(RETURN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Print_stmtContext extends ParserRuleContext {
		public TerminalNode FMT() { return getToken(MiniGoParser.FMT, 0); }
		public TerminalNode PRINT() { return getToken(MiniGoParser.PRINT, 0); }
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public TerminalNode PRINTLN() { return getToken(MiniGoParser.PRINTLN, 0); }
		public Print_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_print_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterPrint_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitPrint_stmt(this);
		}
	}

	public final Print_stmtContext print_stmt() throws RecognitionException {
		Print_stmtContext _localctx = new Print_stmtContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_print_stmt);
		try {
			setState(203);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(189);
				match(FMT);
				setState(190);
				match(T__8);
				setState(191);
				match(PRINT);
				setState(192);
				match(T__3);
				setState(193);
				args();
				setState(194);
				match(T__4);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(196);
				match(FMT);
				setState(197);
				match(T__8);
				setState(198);
				match(PRINTLN);
				setState(199);
				match(T__3);
				setState(200);
				args();
				setState(201);
				match(T__4);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Local_declContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(MiniGoParser.VAR, 0); }
		public TerminalNode IDENT() { return getToken(MiniGoParser.IDENT, 0); }
		public Type_specContext type_spec() {
			return getRuleContext(Type_specContext.class,0);
		}
		public TerminalNode LITERAL() { return getToken(MiniGoParser.LITERAL, 0); }
		public Local_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_local_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterLocal_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitLocal_decl(this);
		}
	}

	public final Local_declContext local_decl() throws RecognitionException {
		Local_declContext _localctx = new Local_declContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_local_decl);
		try {
			setState(214);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(205);
				match(VAR);
				setState(206);
				match(IDENT);
				setState(207);
				type_spec();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(208);
				match(VAR);
				setState(209);
				match(IDENT);
				setState(210);
				match(T__1);
				setState(211);
				match(LITERAL);
				setState(212);
				match(T__2);
				setState(213);
				type_spec();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<TerminalNode> LITERAL() { return getTokens(MiniGoParser.LITERAL); }
		public TerminalNode LITERAL(int i) {
			return getToken(MiniGoParser.LITERAL, i);
		}
		public TerminalNode IDENT() { return getToken(MiniGoParser.IDENT, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public TerminalNode EQ() { return getToken(MiniGoParser.EQ, 0); }
		public TerminalNode NE() { return getToken(MiniGoParser.NE, 0); }
		public TerminalNode LE() { return getToken(MiniGoParser.LE, 0); }
		public TerminalNode GE() { return getToken(MiniGoParser.GE, 0); }
		public TerminalNode AND() { return getToken(MiniGoParser.AND, 0); }
		public TerminalNode OR() { return getToken(MiniGoParser.OR, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(217);
				_la = _input.LA(1);
				if ( !(_la==IDENT || _la==LITERAL) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 2:
				{
				setState(218);
				match(T__3);
				setState(219);
				expr(0);
				setState(220);
				match(T__4);
				}
				break;
			case 3:
				{
				setState(222);
				match(IDENT);
				setState(223);
				match(T__1);
				setState(224);
				expr(0);
				setState(225);
				match(T__2);
				}
				break;
			case 4:
				{
				setState(227);
				match(IDENT);
				setState(228);
				match(T__3);
				setState(229);
				args();
				setState(230);
				match(T__4);
				}
				break;
			case 5:
				{
				setState(232);
				((ExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13))) != 0)) ) {
					((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(233);
				expr(7);
				}
				break;
			case 6:
				{
				setState(234);
				match(LITERAL);
				setState(235);
				match(T__0);
				setState(236);
				match(LITERAL);
				}
				break;
			case 7:
				{
				setState(237);
				match(IDENT);
				setState(238);
				match(T__5);
				setState(239);
				expr(2);
				}
				break;
			case 8:
				{
				setState(240);
				match(IDENT);
				setState(241);
				match(T__1);
				setState(242);
				expr(0);
				setState(243);
				match(T__2);
				setState(244);
				match(T__5);
				setState(245);
				expr(1);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(260);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(258);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(249);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(250);
						((ExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__15) | (1L << T__16))) != 0)) ) {
							((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(251);
						((ExprContext)_localctx).right = expr(7);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(252);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(253);
						((ExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__9 || _la==T__10) ) {
							((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(254);
						((ExprContext)_localctx).right = expr(6);
						}
						break;
					case 3:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(255);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(256);
						((ExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << OR) | (1L << AND) | (1L << LE) | (1L << GE) | (1L << EQ) | (1L << NE))) != 0)) ) {
							((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(257);
						((ExprContext)_localctx).right = expr(5);
						}
						break;
					}
					} 
				}
				setState(262);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ArgsContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_args; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).enterArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniGoListener ) ((MiniGoListener)listener).exitArgs(this);
		}
	}

	public final ArgsContext args() throws RecognitionException {
		ArgsContext _localctx = new ArgsContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_args);
		int _la;
		try {
			setState(272);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__3:
			case T__9:
			case T__10:
			case T__11:
			case T__12:
			case T__13:
			case IDENT:
			case LITERAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(263);
				expr(0);
				setState(268);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(264);
					match(T__0);
					setState(265);
					expr(0);
					}
					}
					setState(270);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 16:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 6);
		case 1:
			return precpred(_ctx, 5);
		case 2:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3,\u0115\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\6\2(\n\2\r\2\16\2)\3\3\3\3\5\3.\n\3\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4>\n\4\3\5\3\5\3\5\5\5C\n\5\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\5\6Y\n\6\3\7\3\7\3\7\3\7\7\7_\n\7\f\7\16\7b\13\7\5\7d\n\7\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\5\bl\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\tu\n\t\3\n\3"+
		"\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\5\13\u0095\n\13\3\f\3\f\7\f\u0099\n\f\f\f\16\f\u009c\13\f\3\f\7\f"+
		"\u009f\n\f\f\f\16\f\u00a2\13\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\5\r\u00b0\n\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\5\17\u00be\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u00ce\n\20\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\5\21\u00d9\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u00fa\n\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\7\22\u0105\n\22\f\22\16\22\u0108"+
		"\13\22\3\23\3\23\3\23\7\23\u010d\n\23\f\23\16\23\u0110\13\23\3\23\5\23"+
		"\u0113\n\23\3\23\2\3\"\24\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$\2"+
		"\7\3\2\'(\3\2\f\20\3\2\21\23\3\2\f\r\4\2\24\25\37$\2\u0128\2\'\3\2\2\2"+
		"\4-\3\2\2\2\6=\3\2\2\2\bB\3\2\2\2\nX\3\2\2\2\fc\3\2\2\2\16k\3\2\2\2\20"+
		"t\3\2\2\2\22v\3\2\2\2\24\u0094\3\2\2\2\26\u0096\3\2\2\2\30\u00af\3\2\2"+
		"\2\32\u00b1\3\2\2\2\34\u00bd\3\2\2\2\36\u00cd\3\2\2\2 \u00d8\3\2\2\2\""+
		"\u00f9\3\2\2\2$\u0112\3\2\2\2&(\5\4\3\2\'&\3\2\2\2()\3\2\2\2)\'\3\2\2"+
		"\2)*\3\2\2\2*\3\3\2\2\2+.\5\6\4\2,.\5\n\6\2-+\3\2\2\2-,\3\2\2\2.\5\3\2"+
		"\2\2/\60\7\27\2\2\60\61\7\'\2\2\61>\5\b\5\2\62\63\7\27\2\2\63\64\7\'\2"+
		"\2\64\65\7\3\2\2\65\66\7\'\2\2\66>\5\b\5\2\678\7\27\2\289\7\'\2\29:\7"+
		"\4\2\2:;\7(\2\2;<\7\5\2\2<>\5\b\5\2=/\3\2\2\2=\62\3\2\2\2=\67\3\2\2\2"+
		">\7\3\2\2\2?C\7\32\2\2@C\7\26\2\2AC\3\2\2\2B?\3\2\2\2B@\3\2\2\2BA\3\2"+
		"\2\2C\t\3\2\2\2DE\7\30\2\2EF\7\'\2\2FG\7\6\2\2GH\5\f\7\2HI\7\7\2\2IJ\5"+
		"\b\5\2JK\5\26\f\2KY\3\2\2\2LM\7\30\2\2MN\7\'\2\2NO\7\6\2\2OP\5\f\7\2P"+
		"Q\7\7\2\2QR\7\6\2\2RS\5\b\5\2ST\7\3\2\2TU\5\b\5\2UV\7\7\2\2VW\5\26\f\2"+
		"WY\3\2\2\2XD\3\2\2\2XL\3\2\2\2Y\13\3\2\2\2Zd\3\2\2\2[`\5\16\b\2\\]\7\3"+
		"\2\2]_\5\16\b\2^\\\3\2\2\2_b\3\2\2\2`^\3\2\2\2`a\3\2\2\2ad\3\2\2\2b`\3"+
		"\2\2\2cZ\3\2\2\2c[\3\2\2\2d\r\3\2\2\2ef\7\'\2\2fl\5\b\5\2gh\7\'\2\2hi"+
		"\7\4\2\2ij\7\5\2\2jl\5\b\5\2ke\3\2\2\2kg\3\2\2\2l\17\3\2\2\2mu\5\22\n"+
		"\2nu\5\26\f\2ou\5\24\13\2pu\5\30\r\2qu\5\32\16\2ru\5\34\17\2su\5\36\20"+
		"\2tm\3\2\2\2tn\3\2\2\2to\3\2\2\2tp\3\2\2\2tq\3\2\2\2tr\3\2\2\2ts\3\2\2"+
		"\2u\21\3\2\2\2vw\5\"\22\2w\23\3\2\2\2xy\7\27\2\2yz\7\'\2\2z{\7\3\2\2{"+
		"|\7\'\2\2|}\5\b\5\2}~\7\b\2\2~\177\7(\2\2\177\u0080\7\3\2\2\u0080\u0081"+
		"\7(\2\2\u0081\u0095\3\2\2\2\u0082\u0083\7\27\2\2\u0083\u0084\7\'\2\2\u0084"+
		"\u0085\5\b\5\2\u0085\u0086\7\b\2\2\u0086\u0087\5\"\22\2\u0087\u0095\3"+
		"\2\2\2\u0088\u0089\7\'\2\2\u0089\u008a\5\b\5\2\u008a\u008b\7\b\2\2\u008b"+
		"\u008c\5\"\22\2\u008c\u0095\3\2\2\2\u008d\u008e\7\'\2\2\u008e\u008f\7"+
		"\4\2\2\u008f\u0090\5\"\22\2\u0090\u0091\7\5\2\2\u0091\u0092\7\b\2\2\u0092"+
		"\u0093\5\"\22\2\u0093\u0095\3\2\2\2\u0094x\3\2\2\2\u0094\u0082\3\2\2\2"+
		"\u0094\u0088\3\2\2\2\u0094\u008d\3\2\2\2\u0095\25\3\2\2\2\u0096\u009a"+
		"\7\t\2\2\u0097\u0099\5 \21\2\u0098\u0097\3\2\2\2\u0099\u009c\3\2\2\2\u009a"+
		"\u0098\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u00a0\3\2\2\2\u009c\u009a\3\2"+
		"\2\2\u009d\u009f\5\20\t\2\u009e\u009d\3\2\2\2\u009f\u00a2\3\2\2\2\u00a0"+
		"\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a3\3\2\2\2\u00a2\u00a0\3\2"+
		"\2\2\u00a3\u00a4\7\n\2\2\u00a4\27\3\2\2\2\u00a5\u00a6\7\34\2\2\u00a6\u00a7"+
		"\5\"\22\2\u00a7\u00a8\5\26\f\2\u00a8\u00b0\3\2\2\2\u00a9\u00aa\7\34\2"+
		"\2\u00aa\u00ab\5\"\22\2\u00ab\u00ac\5\26\f\2\u00ac\u00ad\7\35\2\2\u00ad"+
		"\u00ae\5\26\f\2\u00ae\u00b0\3\2\2\2\u00af\u00a5\3\2\2\2\u00af\u00a9\3"+
		"\2\2\2\u00b0\31\3\2\2\2\u00b1\u00b2\7\33\2\2\u00b2\u00b3\5\"\22\2\u00b3"+
		"\u00b4\5\26\f\2\u00b4\33\3\2\2\2\u00b5\u00b6\7\36\2\2\u00b6\u00b7\5\""+
		"\22\2\u00b7\u00b8\7\3\2\2\u00b8\u00b9\5\"\22\2\u00b9\u00be\3\2\2\2\u00ba"+
		"\u00bb\7\36\2\2\u00bb\u00be\5\"\22\2\u00bc\u00be\7\36\2\2\u00bd\u00b5"+
		"\3\2\2\2\u00bd\u00ba\3\2\2\2\u00bd\u00bc\3\2\2\2\u00be\35\3\2\2\2\u00bf"+
		"\u00c0\7\31\2\2\u00c0\u00c1\7\13\2\2\u00c1\u00c2\7&\2\2\u00c2\u00c3\7"+
		"\6\2\2\u00c3\u00c4\5$\23\2\u00c4\u00c5\7\7\2\2\u00c5\u00ce\3\2\2\2\u00c6"+
		"\u00c7\7\31\2\2\u00c7\u00c8\7\13\2\2\u00c8\u00c9\7%\2\2\u00c9\u00ca\7"+
		"\6\2\2\u00ca\u00cb\5$\23\2\u00cb\u00cc\7\7\2\2\u00cc\u00ce\3\2\2\2\u00cd"+
		"\u00bf\3\2\2\2\u00cd\u00c6\3\2\2\2\u00ce\37\3\2\2\2\u00cf\u00d0\7\27\2"+
		"\2\u00d0\u00d1\7\'\2\2\u00d1\u00d9\5\b\5\2\u00d2\u00d3\7\27\2\2\u00d3"+
		"\u00d4\7\'\2\2\u00d4\u00d5\7\4\2\2\u00d5\u00d6\7(\2\2\u00d6\u00d7\7\5"+
		"\2\2\u00d7\u00d9\5\b\5\2\u00d8\u00cf\3\2\2\2\u00d8\u00d2\3\2\2\2\u00d9"+
		"!\3\2\2\2\u00da\u00db\b\22\1\2\u00db\u00fa\t\2\2\2\u00dc\u00dd\7\6\2\2"+
		"\u00dd\u00de\5\"\22\2\u00de\u00df\7\7\2\2\u00df\u00fa\3\2\2\2\u00e0\u00e1"+
		"\7\'\2\2\u00e1\u00e2\7\4\2\2\u00e2\u00e3\5\"\22\2\u00e3\u00e4\7\5\2\2"+
		"\u00e4\u00fa\3\2\2\2\u00e5\u00e6\7\'\2\2\u00e6\u00e7\7\6\2\2\u00e7\u00e8"+
		"\5$\23\2\u00e8\u00e9\7\7\2\2\u00e9\u00fa\3\2\2\2\u00ea\u00eb\t\3\2\2\u00eb"+
		"\u00fa\5\"\22\t\u00ec\u00ed\7(\2\2\u00ed\u00ee\7\3\2\2\u00ee\u00fa\7("+
		"\2\2\u00ef\u00f0\7\'\2\2\u00f0\u00f1\7\b\2\2\u00f1\u00fa\5\"\22\4\u00f2"+
		"\u00f3\7\'\2\2\u00f3\u00f4\7\4\2\2\u00f4\u00f5\5\"\22\2\u00f5\u00f6\7"+
		"\5\2\2\u00f6\u00f7\7\b\2\2\u00f7\u00f8\5\"\22\3\u00f8\u00fa\3\2\2\2\u00f9"+
		"\u00da\3\2\2\2\u00f9\u00dc\3\2\2\2\u00f9\u00e0\3\2\2\2\u00f9\u00e5\3\2"+
		"\2\2\u00f9\u00ea\3\2\2\2\u00f9\u00ec\3\2\2\2\u00f9\u00ef\3\2\2\2\u00f9"+
		"\u00f2\3\2\2\2\u00fa\u0106\3\2\2\2\u00fb\u00fc\f\b\2\2\u00fc\u00fd\t\4"+
		"\2\2\u00fd\u0105\5\"\22\t\u00fe\u00ff\f\7\2\2\u00ff\u0100\t\5\2\2\u0100"+
		"\u0105\5\"\22\b\u0101\u0102\f\6\2\2\u0102\u0103\t\6\2\2\u0103\u0105\5"+
		"\"\22\7\u0104\u00fb\3\2\2\2\u0104\u00fe\3\2\2\2\u0104\u0101\3\2\2\2\u0105"+
		"\u0108\3\2\2\2\u0106\u0104\3\2\2\2\u0106\u0107\3\2\2\2\u0107#\3\2\2\2"+
		"\u0108\u0106\3\2\2\2\u0109\u010e\5\"\22\2\u010a\u010b\7\3\2\2\u010b\u010d"+
		"\5\"\22\2\u010c\u010a\3\2\2\2\u010d\u0110\3\2\2\2\u010e\u010c\3\2\2\2"+
		"\u010e\u010f\3\2\2\2\u010f\u0113\3\2\2\2\u0110\u010e\3\2\2\2\u0111\u0113"+
		"\3\2\2\2\u0112\u0109\3\2\2\2\u0112\u0111\3\2\2\2\u0113%\3\2\2\2\27)-="+
		"BX`ckt\u0094\u009a\u00a0\u00af\u00bd\u00cd\u00d8\u00f9\u0104\u0106\u010e"+
		"\u0112";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}