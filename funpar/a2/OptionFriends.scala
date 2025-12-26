object OptionFriends extends App {
  def lookup(xs: List[(String, String)], key: String): Option[String] =
    xs.find{ case (k,v) => k == key }.map{ case (k, v) => v }

  // resolve function itself
  def resolve(userIdFromLoginName: String => Option[String],
              majorFromUserId: String => Option[String],
              divisionFromMajor: String => Option[String],
              averageScoreFromDivision: String => Option[Double],
              loginName: String): Double = {
    userIdFromLoginName(loginName).
      flatMap(userId => majorFromUserId(userId)).
      flatMap(major => divisionFromMajor(major)).
      flatMap(division => averageScoreFromDivision(division)).
      getOrElse(0.0)
  }

}
