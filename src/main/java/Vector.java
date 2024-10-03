public class Vector {
    private double[] doubElements;

    public Vector(double[] _elements) {
        doubElements = _elements;
    }
    public double getElementatIndex(int _index) {
        if(_index >= doubElements.length)
            return -1;
        return doubElements[_index];
    }
    public void setElementatIndex(double _value, int _index)
    {
        if(_index >= doubElements.length)
            doubElements[doubElements.length-1] = _value;
        else
        {
            doubElements[_index] = _value;
        }
    }
    public double[] getAllElements()
    {
        return doubElements;
    }
    public int getVectorSize()
    {
        return doubElements.length;
    }
    public Vector reSize(int _size)
    {

        if(_size == getVectorSize() || _size <= 0){
            return new Vector(doubElements);
        }
        else if(_size<getVectorSize()){
            double [] newElements = new double[_size];
            for (int i = 0;i< _size;i++){
                newElements[i] = doubElements[i];
            }
            return new Vector(newElements);
        }
        else
        {
            double [] newElements = new double[_size];
            for (int i=0;i<getVectorSize();i++)
            {
                newElements[i]=doubElements[i];
            }
            for (int i=getVectorSize();i<_size;i++)
            {
                newElements[i]=-1;
            }
            return new Vector(newElements);
        }
    }

    public Vector add(Vector _v) {
        Vector vector = new Vector(doubElements);
        if(_v.getVectorSize()>vector.getVectorSize())
            vector=vector.reSize(_v.getVectorSize());
        else if(vector.getVectorSize()>_v.getVectorSize())
            _v=_v.reSize(vector.getVectorSize());
        for (int i=0;i< _v.getVectorSize();i++)
            vector.doubElements[i]+=_v.doubElements[i];
        return new Vector(vector.doubElements);
    }
    public Vector subtraction(Vector _v) {
        Vector vector = new Vector(doubElements);
        if(_v.getVectorSize()>vector.getVectorSize())
            vector=vector.reSize(_v.getVectorSize());
        else if(vector.getVectorSize()>_v.getVectorSize())
            _v=_v.reSize(vector.getVectorSize());
        for (int i=0;i< _v.getVectorSize();i++)
            vector.doubElements[i]-=_v.doubElements[i];
        return new Vector(vector.doubElements);

    }

    public double dotProduct(Vector _v) {
        double product=0;
        Vector vector = new Vector(doubElements);
        if(_v.getVectorSize()>vector.getVectorSize())
            vector=vector.reSize(_v.getVectorSize());
        else if(vector.getVectorSize()>_v.getVectorSize())
            _v=_v.reSize(vector.getVectorSize());
        for (int i=0;i< _v.getVectorSize();i++)
            product=product+vector.doubElements[i]*_v.doubElements[i];
        return product;
    }
    public double cosineSimilarity(Vector _v) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        Vector vector = new Vector(doubElements);
        if(_v.getVectorSize() > vector.getVectorSize())
            vector = vector.reSize(_v.getVectorSize());
        else if(vector.getVectorSize() > _v.getVectorSize())
            _v= _v.reSize(vector.getVectorSize());
        for (int i = 0; i < vector.getVectorSize(); i++)
        {
            dotProduct += vector.doubElements[i] * _v.doubElements[i];
            normA += Math.pow(vector.doubElements[i], 2);
            normB += Math.pow(_v.doubElements[i], 2);
        }
            return dotProduct * Math.pow(Math.sqrt(normA) * Math.sqrt(normB),-1);
    }
    @Override
    public boolean equals(Object _obj)
    {
        Vector v = (Vector) _obj;
        boolean boolEquals = true;
        if(v.doubElements.length!=doubElements.length)
            return false;
        for(int i=0;i<doubElements.length;i++)
        {
            if(v.doubElements[i] != doubElements[i])
                return false;
        }
        return true;
    }
    @Override
    public String toString() {
        StringBuilder mySB = new StringBuilder();
        for (int i = 0; i < this.getVectorSize(); i++) {
            mySB.append(String.format("%.5f", doubElements[i])).append(",");
        }
        mySB.delete(mySB.length() - 1, mySB.length());
        return mySB.toString();
    }
}
