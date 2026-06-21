import java.lang.System;
import java_cup.runtime.*;


class scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final byte YYEOF = -1;
	private java.io.DataInputStream yy_instream;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private byte yy_buffer[];
	private int yy_lexical_state;
	scanner (java.io.InputStream instream) {
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_instream = new java.io.DataInputStream(instream);
		yy_buffer = new byte[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_lexical_state = YYINITIAL;
	}
	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private byte yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_instream.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YYEOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_instream.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YYEOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_start () {
		++yy_buffer_start;
	}
	private void yy_pushback () {
		--yy_buffer_end;
	}
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,0,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private byte[] yy_double (byte buf[]) {
		int i;
		byte newbuf[];
		newbuf = new byte[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int yy_acpt[] = {
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR
	};
	private int yy_cmap[] = {
		0, 0, 0, 0, 0, 0, 0, 0,
		1, 1, 1, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0,
		1, 0, 2, 0, 0, 0, 0, 3,
		4, 5, 0, 0, 0, 0, 6, 0,
		7, 7, 7, 7, 7, 7, 7, 7,
		7, 7, 8, 9, 0, 10, 0, 0,
		11, 12, 7, 7, 7, 13, 14, 7,
		7, 15, 7, 7, 16, 17, 18, 7,
		7, 7, 19, 20, 21, 22, 7, 7,
		7, 7, 7, 0, 0, 0, 0, 7,
		0, 7, 7, 7, 7, 7, 7, 7,
		7, 7, 7, 7, 7, 7, 7, 7,
		7, 7, 7, 7, 7, 7, 7, 7,
		7, 7, 7, 23, 0, 24, 0, 0
		
	};
	private int yy_rmap[] = {
		0, 1, 2, 3, 2, 2, 2, 2,
		2, 2, 2, 2, 2, 2, 4, 5,
		6, 7, 8, 9, 10, 11, 12, 13,
		14, 15, 16, 17, 18, 19, 20, 21,
		22, 23, 24, 25 
	};
	private int yy_nxt[][] = {
		{ -1, 1, 14, 16, 18, -1, 2, 3,
			-1, 4, 5, 6, 3, 3, 34, 3,
			3, 35, 3, 3, 3, 3, 27, 20,
			-1 
		},
		{ -1, 1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1,
			-1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			-1, -1, -1, -1, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 3, -1,
			-1 
		},
		{ 14, 14, 7, 14, 14, 14, 14, 14,
			14, 14, 14, 14, 14, 14, 14, 14,
			14, 14, 14, 14, 14, 14, 14, 14,
			14 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			11, -1, -1, -1, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 3, -1,
			-1 
		},
		{ 16, 16, 16, 8, 16, 16, 16, 16,
			16, 16, 16, 16, 16, 16, 16, 16,
			16, 16, 16, 16, 16, 16, 16, 16,
			16 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			12, -1, -1, -1, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 3, -1,
			-1 
		},
		{ 18, 18, 18, 18, 18, 9, 18, 18,
			18, 18, 18, 18, 18, 18, 18, 18,
			18, 18, 18, 18, 18, 18, 18, 18,
			18 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			13, -1, -1, -1, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 3, -1,
			-1 
		},
		{ 20, 20, 20, 20, 20, 20, 20, 20,
			20, 20, 20, 20, 20, 20, 20, 20,
			20, 20, 20, 20, 20, 20, 20, 20,
			10 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			-1, -1, -1, -1, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 15, 3, -1,
			-1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			-1, -1, -1, -1, 3, 3, 3, 3,
			3, 3, 3, 3, 17, 3, 3, -1,
			-1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			-1, -1, -1, -1, 3, 19, 3, 3,
			3, 3, 3, 3, 3, 3, 3, -1,
			-1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			-1, -1, -1, -1, 3, 3, 3, 21,
			3, 3, 3, 3, 3, 3, 3, -1,
			-1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			-1, -1, -1, -1, 3, 3, 3, 3,
			3, 3, 3, 22, 3, 3, 3, -1,
			-1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			-1, -1, -1, -1, 3, 3, 3, 3,
			3, 3, 3, 23, 3, 3, 3, -1,
			-1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			-1, -1, -1, -1, 3, 3, 3, 3,
			3, 3, 24, 3, 3, 3, 3, -1,
			-1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			-1, -1, -1, -1, 3, 25, 3, 3,
			3, 3, 3, 3, 3, 3, 3, -1,
			-1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			-1, -1, -1, -1, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 26, -1,
			-1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			-1, -1, -1, -1, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 28, 3, -1,
			-1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			-1, -1, -1, -1, 3, 3, 3, 3,
			3, 3, 3, 3, 29, 3, 3, -1,
			-1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			-1, -1, -1, -1, 3, 3, 3, 3,
			30, 3, 3, 3, 3, 3, 3, -1,
			-1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			-1, -1, -1, -1, 31, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 3, -1,
			-1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			-1, -1, -1, -1, 3, 3, 3, 32,
			3, 3, 3, 3, 3, 3, 3, -1,
			-1 
		},
		{ -1, -1, -1, -1, -1, -1, -1, 3,
			-1, -1, -1, -1, 3, 33, 3, 3,
			3, 3, 3, 3, 3, 3, 3, -1,
			-1 
		}
	};
	public token next_token ()
		throws java.io.IOException {
		byte yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			if (YYEOF != yy_lookahead) {
				yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YYEOF == yy_lookahead && true == yy_initial) {
					return null;
				}
				else if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_to_mark();
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_pushback();
					}
					if (0 != (YY_START & yy_anchor)) {
						yy_move_start();
					}
					switch (yy_last_accept_state) {
					case 1:
						{ System.out.print(yytext()); }
					case -2:
						break;
					case 2:
						{ System.out.print(yytext()); return(new token(sym.PERIOD)); }
					case -3:
						break;
					case 3:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -4:
						break;
					case 4:
						{ System.out.print(yytext()); return(new token(sym.SEMICOLON)); }
					case -5:
						break;
					case 5:
						{ System.out.print(yytext()); return(new token(sym.EQUALS)); }
					case -6:
						break;
					case 6:
						{ System.out.print(yytext()); return(new token(sym.AT)); }
					case -7:
						break;
					case 7:
						{ String str =  yytext().substring(1,yytext().length()-1);
    System.out.print(yytext());  
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -8:
						break;
					case 8:
						{ String str =  yytext().substring(1,yytext().length()-1);
    System.out.print(yytext());  
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -9:
						break;
					case 9:
						{ System.out.print(yytext()); 
    String str = yytext().substring(1,yytext().length() - 1);
    return(new str_token(sym.REGULAR_EXPRESSION, str)); 
  }
					case -10:
						break;
					case 10:
						{ System.out.print(yytext()); 
    String str = yytext().substring(1,yytext().length() - 1);
    return(new str_token(sym.REGULAR_EXPRESSION, str)); 
  }
					case -11:
						break;
					case 11:
						{ System.out.print(yytext()); return(new token(sym.UNIT)); }
					case -12:
						break;
					case 12:
						{ System.out.print(yytext()); return(new token(sym.FILTERS)); }
					case -13:
						break;
					case 13:
						{ System.out.print(yytext()); return(new token(sym.MEASURE)); }
					case -14:
						break;
					case 15:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -15:
						break;
					case 17:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -16:
						break;
					case 19:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -17:
						break;
					case 21:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -18:
						break;
					case 22:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -19:
						break;
					case 23:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -20:
						break;
					case 24:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -21:
						break;
					case 25:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -22:
						break;
					case 26:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -23:
						break;
					case 27:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -24:
						break;
					case 28:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -25:
						break;
					case 29:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -26:
						break;
					case 30:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -27:
						break;
					case 31:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -28:
						break;
					case 32:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -29:
						break;
					case 33:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -30:
						break;
					case 34:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -31:
						break;
					case 35:
						{ String str =  yytext().substring(0,yytext().length());
    System.out.print(yytext());     
    return(new str_token(sym.IDENTIFIER, str)); 
  }
					case -32:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
					}
				}
			}
		}
	}
}
