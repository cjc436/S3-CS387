package s3.util;

import java.util.Objects;

public class Pair<T1,T2> {
	public T1 m_a;
	public T2 m_b;
	
	public Pair(T1 a,T2 b) {
		m_a = a;
		m_b = b;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pair<?, ?> pair = (Pair<?, ?>) o;
		return Objects.equals(m_a, pair.m_a) && Objects.equals(m_b, pair.m_b);
	}

	@Override
	public int hashCode() {
		return Objects.hash(m_a, m_b);
	}
}
