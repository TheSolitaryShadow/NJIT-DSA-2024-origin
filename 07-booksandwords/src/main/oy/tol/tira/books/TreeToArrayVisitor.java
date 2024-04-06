package oy.tol.tira.books;
class TreeToArrayVisitor implements Visitor {

   private WordCount [] elements;
   private int count = 0;

   public TreeToArrayVisitor(int count) {
      elements = (WordCount[])new WordCount[count];
      count = 0;
   }

   @Override
   public void visit(TreeNode node) {
      if (node.left != null) {
         node.left.accept(this);
      }
      elements[count++] = new WordCount(node.keyValue.getWord(), node.keyValue.getCount());
      if (node.list != null) {
         for (int index = 0; index < node.list.size(); index++) {
            WordCount item = node.list.get(index);
            elements[count++] = new WordCount(item.getWord(), item.getCount());
         }
      }
      if (node.right != null) {
         node.right.accept(this);
      }
   }

   WordCount [] getArray() {
      return elements;
   }
   
}
